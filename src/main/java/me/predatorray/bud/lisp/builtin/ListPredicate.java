package me.predatorray.bud.lisp.builtin;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.util.List;

public class ListPredicate extends Predicate {

    public ListPredicate() {
        super("list?");
    }

    @Override
    protected void checkArgumentTypes(List<BudType> argumentTypes) {
        if (argumentTypes.size() != 1) {
            throw new EvaluatingException("requires 1 argument");
        }
    }

    @Override
    protected boolean predicate(List<BudObject> arguments) {
        return BudType.Category.LIST.equals(arguments.get(0).getType().getCategory());
    }
}
