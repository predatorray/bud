package me.predatorray.bud.lisp.buitin;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.util.List;

public class EqPredicate extends Predicate {

    public EqPredicate() {
        super("eq?");
    }

    @Override
    protected void checkArgumentTypes(List<BudType> argumentTypes) {
        if (argumentTypes.size() != 2) {
            throw new EvaluatingException("requires 2 arguments");
        }
    }

    @Override
    protected boolean predicate(List<BudObject> arguments) {
        return arguments.get(0) == arguments.get(1);
    }
}
