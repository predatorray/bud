package me.predatorray.bud.lisp.buitin;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.FunctionType;

import java.util.List;

public class BooleanPredicate extends Predicate {

    private final FunctionType thisType = new FunctionType(this);

    @Override
    protected void checkArgumentTypes(List<BudType> argumentTypes) {
        if (argumentTypes.size() != 1) {
            throw new EvaluatingException("requires 1 argument");
        }
    }

    @Override
    protected boolean predicate(List<BudObject> arguments) {
        return BudType.BOOLEAN.equals(arguments.get(0).getType());
    }

    @Override
    public BudType getType() {
        return thisType;
    }
}
