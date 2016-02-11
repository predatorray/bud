package me.predatorray.bud.lisp.evaluator;

import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.parser.Expression;

public class NaiveEvaluator implements Evaluator {

    @Override
    public BudObject evaluate(Expression expression, Environment environment) {
        return expression.evaluate(environment);
    }
}
