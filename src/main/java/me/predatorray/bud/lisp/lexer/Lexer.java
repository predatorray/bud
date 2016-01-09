package me.predatorray.bud.lisp.lexer;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Lexer implements Iterator<Token> {

    private final CharSequence lispProgram;
    private int currIndex = 0;
    private TokenizerState state = TokenizerState.NORMAL;

    public Lexer(CharSequence lispProgram) {
        if (lispProgram == null) {
            throw new NullPointerException();
        }
        this.lispProgram = lispProgram;
    }

    public boolean hasNext() {
        return currIndex < lispProgram.length();
    }

    public Token next() {
        if (currIndex >= lispProgram.length()) {
            throw new NoSuchElementException();
        }

        while (true) {
            char currChar = lispProgram.charAt(currIndex);
            switch (state) {
                case DOUBLE_QUOTED:
                    // TODO
                    switch (currChar) {
                        case '\"':
                            return null; // TODO return unescape(buffer.toString())
                        default:
                            // TODO buffer.append()
                    }
                    break;
                case NORMAL:
                default:
                    switch (currChar) {
                        case '(':
                            return new LeftParenthesis(null);
                        case ')':
                            return new RightParenthesis(null);
                        case ' ':
                        case '\n':
                        case '\r':
                        case '\t':
                            return new WhiteSpace(null);
                        case '\'':
                            return new SingleQuoteToken(null);
                        case '\"':
                            if (isTheLastChar()) {
                                throw new RuntimeException("EOF"); // TODO
                            }
                            state = TokenizerState.DOUBLE_QUOTED;
                            break;
                        default:
                            // TODO if is legal character

                    }
                    break;
            }
            ++currIndex;
        }
    }

    private boolean isTheLastChar() {
        return currIndex == lispProgram.length() - 1;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    private enum TokenizerState {

        NORMAL, DOUBLE_QUOTED
    }
}
