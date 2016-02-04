package me.predatorray.bud.lisp.parser;

public class NotApplicableException extends ParserException {

    public NotApplicableException(Expression expression) {
        super("expression is not applicable", expression);
    }
}
