package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.Token;

public class ParserException extends RuntimeException {

    public ParserException() {
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Token token) {
        super(message + ": " + token + " at " + token.getLocation());
    }

    public ParserException(String message, Expression expression) {
        super(message + ": " + expression + " at " + expression.getLocation());
    }
}
