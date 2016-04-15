package me.predatorray.bud.lisp.evaluator;

import me.predatorray.bud.lisp.lang.BudFuture;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.parser.Expression;

public class TcoEvaluator implements Evaluator {

    @Override
    public BudObject evaluate(Expression expression, Environment environment) {
        BudFuture nextCall = expression.evaluateAndGetBudFuture(environment, this);
        while (true) {
            BudFuture tailCall = nextCall.getTailCall();
            if (tailCall == null) {
                return nextCall.getResult();
            }
            nextCall = tailCall;
        }
    }
}
