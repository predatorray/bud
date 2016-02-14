package me.predatorray.bud.lisp.lexer;

import me.predatorray.bud.lisp.util.Sets;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
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
                throw new RuntimeException("EOF"); // TODO EOF
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
                        if (isEscapeSequence(currChar)) {
                            state = TokenizerState.WITHIN_DOUBLE_QUOTES;
                            Character unescaped = ESCAPE_SEQUENCE_MAPPING.get(currChar);
                            if (unescaped == null) {
                                throw new IllegalStateException("unknown escape sequence: " + currChar);
                            }
                            doubleQuotedValue.append(unescaped);
                        } else if (currChar == '\n') {
                            state = TokenizerState.BACKSLASH_EOL;
                        } else {
                            throw new RuntimeException("not a valid escape sequence: " + currChar);
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
                                throw new RuntimeException("not a number: " + number);
                            }
                        }
                        break;
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
                                    throw new RuntimeException("not a number: " + number);
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

    private static final Map<Character, Character> ESCAPE_SEQUENCE_MAPPING = new HashMap<>();
    static {
        ESCAPE_SEQUENCE_MAPPING.put('n', '\n');
        ESCAPE_SEQUENCE_MAPPING.put('t', '\t');
        ESCAPE_SEQUENCE_MAPPING.put('b', '\b');
        ESCAPE_SEQUENCE_MAPPING.put('f', '\f');
        ESCAPE_SEQUENCE_MAPPING.put('r', '\r');
        ESCAPE_SEQUENCE_MAPPING.put('\"', '\"');
        ESCAPE_SEQUENCE_MAPPING.put('\'', '\'');
        ESCAPE_SEQUENCE_MAPPING.put('\\', '\\');
    }

    private static final Set<Character> ESCAPE_SEQUENCES = ESCAPE_SEQUENCE_MAPPING.keySet();

    private static boolean isEscapeSequence(char c) {
        return ESCAPE_SEQUENCES.contains(c);
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
     */
    private enum TokenizerState {

        NORMAL, WITHIN_DOUBLE_QUOTES, AFTER_BACKSLASH, BACKSLASH_EOL, WITHIN_NUMBER
    }
}
