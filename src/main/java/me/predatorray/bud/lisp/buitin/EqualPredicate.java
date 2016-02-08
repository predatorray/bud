package me.predatorray.bud.lisp.buitin;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.FunctionType;

import java.util.List;

public class EqualPredicate extends Predicate {

    private final BudType thisType;

    public EqualPredicate() {
        thisType = new FunctionType(this);
    }

    @Override
    protected void checkArgumentTypes(List<BudType> argumentTypes) {
        if (argumentTypes.size() != 2) {
            throw new EvaluatingException("requires 2 arguments");
        }
    }

    @Override
    protected boolean predicate(List<BudObject> arguments) {
        return arguments.get(0).equals(arguments.get(1));
    }

    @Override
    public BudType getType() {
        return thisType;
    }
}
