package me.predatorray.bud.lisp.evaluator;

import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.cont.Continuation;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.parser.Expression;

public class ContinuationEvaluator implements Evaluator {

    @Override
    public BudObject evaluate(Expression expression, Environment environment) {
        Continuation cont = expression.evalCont(environment, this);
        BudObject result = null;
        while (cont != null) {
            result = cont.run();
            cont = cont.handle(result);
        }
        return result;
    }
}
