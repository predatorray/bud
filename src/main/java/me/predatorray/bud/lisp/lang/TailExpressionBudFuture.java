package me.predatorray.bud.lisp.lang;

import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.util.Validation;

public final class TailExpressionBudFuture implements BudFuture {

    private final Expression tailExpression;
    private final Environment environment;
    private final Evaluator evaluator;

    public TailExpressionBudFuture(Expression tailExpression, Environment environment, Evaluator evaluator) {
        this.tailExpression = Validation.notNull(tailExpression);
        this.environment = Validation.notNull(environment);
        this.evaluator = Validation.notNull(evaluator);
    }

    @Override
    public BudObject getResult() {
        return null;
    }

    @Override
    public BudFuture getTailCall() {
        return tailExpression.evaluateAndGetBudFuture(environment, evaluator);
    }
}
