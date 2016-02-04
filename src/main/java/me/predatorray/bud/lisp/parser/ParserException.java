package me.predatorray.bud.lisp.parser;

public class ParserException extends RuntimeException {

    public ParserException() {
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Expression expression) {
        super(message + ": " + expression + " at " + expression.getLocation());
    }
}
