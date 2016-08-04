package me.predatorray.bud.lisp.lang.cont;

import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.parser.Expression;

public class EvalExpressionCont implements Continuation {

    private final Expression expression;
    private final Environment environment;
    private final Evaluator evaluator;

    public EvalExpressionCont(Expression expression, Environment environment, Evaluator evaluator) {
        this.expression = expression;
        this.environment = environment;
        this.evaluator = evaluator;
    }

    @Override
    public BudObject run() {
        return null;
    }

    @Override
    public Continuation handle(BudObject ignored) {
        return expression.evalCont(environment, evaluator);
    }
}
