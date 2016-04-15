package me.predatorray.bud.lisp.builtin;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.util.List;

public abstract class OneArgumentPredicate extends Predicate {

    public OneArgumentPredicate(String ...names) {
        super(names);
    }

    @Override
    protected final void checkArgumentTypes(List<BudType> argumentTypes) {
        if (argumentTypes.size() != 1) {
            throw new EvaluatingException("requires one argument");
        }
        checkArgumentTypes(argumentTypes.get(0));
    }

    @Override
    protected final boolean predicate(List<BudObject> arguments) {
        return predicate(arguments.get(0));
    }

    protected void checkArgumentTypes(BudType argumentType) {}

    protected abstract boolean predicate(BudObject argument);
}
