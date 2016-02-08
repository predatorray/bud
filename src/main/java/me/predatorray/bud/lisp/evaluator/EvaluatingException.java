package me.predatorray.bud.lisp.evaluator;

import me.predatorray.bud.lisp.parser.Expression;

public class EvaluatingException extends RuntimeException {

    public EvaluatingException(String message) {
        super(message);
    }

    public EvaluatingException(String message, Expression expression) {
        super(message + ": " + expression + " at " + expression.getLocation());
    }
}
