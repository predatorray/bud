package me.predatorray.bud.lisp.lexer;

import me.predatorray.bud.lisp.util.Sets;
import me.predatorray.bud.lisp.util.StringEscapeUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class Lexer implements Iterable<Token> {

    private final CharSequence sourceCode;

    public Lexer(CharSequence sourceCode) {
        if (sourceCode == null) {
            throw new NullPointerException();
        }
        this.sourceCode = sourceCode;
    }

    public Iterator<Token> iterator() {
        return new LexerIterator(sourceCode);
    }
}

class LexerIterator implements Iterator<Token> {

    private final CharSequence source;

    private int currIndex = -1;
    private TokenizerState state = TokenizerState.NORMAL;
    private StringBuilder symbolValue = new StringBuilder();
    private StringBuilder doubleQuotedValue = new StringBuilder();
    private StringBuilder numberValue = new StringBuilder();

    private int[] location = new int[] {1, 0};
    private int[] doubleQuoteLocation = new int[2];
    private int[] symbolLocation = new int[2];
    private int[] numberLocation = new int[2];
    private int[] boolOrCharLocation = new int[2];

    public LexerIterator(CharSequence source) {
        if (source == null) {
            throw new NullPointerException();
        }
        this.source = source;
    }

    public boolean hasNext() {
        return currIndex + 1 < source.length();
    }

    public Token next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        while (true) {
            ++currIndex;
            ++location[1];

            if (currIndex >= source.length()) {
                // eof
                switch (state) {
                    case WITHIN_DOUBLE_QUOTES:
                        throw new LexerException("Double quotes are not closed, but EOF has been reached.");
                    case AFTER_BACKSLASH:
                    case BACKSLASH_EOL:
                        throw new LexerException("More characters after backslash are expected, " +
                                "but EOF has been reached.");
                    default:
                        throw new LexerException("Unexpected EOF");
                }
            }

            char currChar = source.charAt(currIndex);

            try {
                switch (state) {
                    case WITHIN_DOUBLE_QUOTES:
                        switch (currChar) {
                            case '\"':
                                state = TokenizerState.NORMAL;
                                return new StringToken(popDoubleQuotedValue(), getCurrentDoubleQuoteLocation());
                            case '\\':
                                state = TokenizerState.AFTER_BACKSLASH;
                                break;
                            default:
                                doubleQuotedValue.append(currChar);
                                break;
                        }
                        break;
                    case AFTER_BACKSLASH:
                        if (StringEscapeUtils.isEscapeSequence(currChar)) {
                            state = TokenizerState.WITHIN_DOUBLE_QUOTES;
                            Character unescaped = StringEscapeUtils.unescape(currChar);
                            if (unescaped == null) {
                                throw new LexerException("unknown escape sequence: " + currChar);
                            }
                            doubleQuotedValue.append(unescaped);
                        } else if (currChar == '\n') {
                            state = TokenizerState.BACKSLASH_EOL;
                        } else {
                            throw new LexerException("not a valid escape sequence: " + currChar);
                        }
                        break;
                    case BACKSLASH_EOL:
                        if (isWhiteSpace(currChar)) {
                            break;
                        } else if (currChar == '\\') {
                            state = TokenizerState.AFTER_BACKSLASH;
                        } else if ('"' == currChar) {
                            state = TokenizerState.NORMAL;
                            return new StringToken(popDoubleQuotedValue(), getCurrentDoubleQuoteLocation());
                        } else {
                            doubleQuotedValue.append(currChar);
                            state = TokenizerState.WITHIN_DOUBLE_QUOTES;
                            break;
                        }
                    case WITHIN_NUMBER:
                        numberValue.append(currChar);
                        if (isEndOfSymbolOrNumber()) {
                            String number = popNumberValue();
                            state = TokenizerState.NORMAL;
                            try {
                                BigDecimal decimal = new BigDecimal(number);
                                return new NumberToken(decimal, getCurrentNumberLocation());
                            } catch (NumberFormatException notANumber) {
                                throw new LexerException("not a number: " + number);
                            }
                        }
                        break;
                    case AFTER_SHARP:
                        // TODO
                        if (currChar == 't' || currChar == 'T') {
                            state = TokenizerState.NORMAL;
                            return new BooleanToken(true, getBoolOrCharLocation());
                        }
                        if (currChar == 'f' || currChar == 'F') {
                            state = TokenizerState.NORMAL;
                            return new BooleanToken(false, getBoolOrCharLocation());
                        }
                        if (currChar == '\\') {
                            // TODO state = CHAR
                        }
                        throw new LexerException("not a valid character after #: " + currChar);
                    case NORMAL:
                    default:
                        if (isWhiteSpace(currChar)) {
                            return new Atmosphere(getCurrentCharLocation());
                        }
                        if (PREFIXES_OF_NUMBER.contains(currChar)) {
                            numberValue.append(currChar);
                            state = TokenizerState.WITHIN_NUMBER;
                            numberLocation = location.clone();
                            if (isEndOfSymbolOrNumber()) {
                                String number = popNumberValue();
                                state = TokenizerState.NORMAL;
                                try {
                                    BigDecimal decimal = new BigDecimal(number);
                                    return new NumberToken(decimal, getCurrentNumberLocation());
                                } catch (NumberFormatException notANumber) {
                                    throw new LexerException("not a number: " + number);
                                }
                            }
                            break;
                        }
                        switch (currChar) {
                            case '(':
                                return new LeftParenthesis(getCurrentCharLocation());
                            case ')':
                                return new RightParenthesis(getCurrentCharLocation());
                            case '\'':
                                return new SingleQuoteToken(getCurrentCharLocation());
                            case '\"':
                                doubleQuoteLocation = location.clone();
                                state = TokenizerState.WITHIN_DOUBLE_QUOTES;
                                break;
                            case '#':
                                boolOrCharLocation = location.clone();
                                state = TokenizerState.AFTER_SHARP;
                                break;
                            default:
                                if (symbolValue.length() == 0) {
                                    symbolLocation = location.clone();
                                }
                                symbolValue.append(currChar);
                                if (isEndOfSymbolOrNumber()) {
                                    return new IdentifierToken(popSymbolValue(), getCurrentSymbolLocation());
                                }
                        }
                        break;
                }
            } finally {
                if (currChar == '\n') {
                    ++location[0];
                    location[1] = 0;
                }
            }
        }
    }

    private TextLocation getCurrentCharLocation() {
        return new TextLocation(location[0], location[1]);
    }

    private TextLocation getCurrentDoubleQuoteLocation() {
        return new TextLocation(doubleQuoteLocation[0], doubleQuoteLocation[1]);
    }

    private TextLocation getCurrentSymbolLocation() {
        return new TextLocation(symbolLocation[0], symbolLocation[1]);
    }

    private TextLocation getCurrentNumberLocation() {
        return new TextLocation(numberLocation[0], numberLocation[1]);
    }

    private TextLocation getBoolOrCharLocation() {
        return new TextLocation(boolOrCharLocation[0], boolOrCharLocation[1]);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    private String popSymbolValue() {
        String symbol = symbolValue.toString();
        symbolValue.setLength(0);
        return symbol;
    }

    private String popDoubleQuotedValue() {
        String stringVal = doubleQuotedValue.toString();
        doubleQuotedValue.setLength(0);
        return stringVal;
    }

    private String popNumberValue() {
        String number = numberValue.toString();
        numberValue.setLength(0);
        return number;
    }

    private static final Set<Character> WHITESPACES = Sets.asSet(' ', '\n', '\t', '\r');

    private boolean isWhiteSpace(char c) {
        return WHITESPACES.contains(c);
    }

    private static final Set<Character> DELIMITERS = Sets.union(WHITESPACES, Sets.asSet('(', ')', '"', ';'));

    private boolean isEndOfSymbolOrNumber() {
        int nextIndex = currIndex + 1;
        return (nextIndex >= source.length()) || DELIMITERS.contains(source.charAt(nextIndex));
    }

    private static final Set<Character> NUMBERS = new HashSet<>(10);
    static {
        for (char i = '0'; i <= '9'; i++) {
            NUMBERS.add(i);
        }
    }

    private static final Set<Character> PREFIXES_OF_NUMBER = Sets.union(NUMBERS, Sets.asSet('.'));

    /*
     * state machine of string parsing:
     *
     *            "                             \                       NEW_LINE
     * (NORMAL) =====> (WITHIN_DOUBLE_QUOTES) =====> (AFTER_BACKSLASH) ===========> (BACKSLASH_EOL)
     *
     *   <=>                    <=>                                                      <=>
     *   [^"]                   [^"\]                                                    WS
     *
     *          <=====                        <=====                   <===========
     *             "                          ESC_SEQ                        \
     *
     *                                        <====================================
     *                                                      [^WS"\]
     *
     *          <==================================================================
     *                                           "
     *
     * state machine of boolean/character parsing:
     *
     *            #                    \
     * (NORMAL) =====> (AFTER_SHARP) =====> (CHAR)
     *          <=====
     *           [tf]
     *
     *          <==========================
     *                    [CHAR]
     */
    private enum TokenizerState {

        NORMAL,
        WITHIN_DOUBLE_QUOTES, AFTER_BACKSLASH, BACKSLASH_EOL, WITHIN_NUMBER,
        AFTER_SHARP
    }
}
