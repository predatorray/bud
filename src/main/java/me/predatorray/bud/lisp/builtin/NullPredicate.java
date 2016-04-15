package me.predatorray.bud.lisp.builtin;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudList;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.util.List;

public class NullPredicate extends Predicate {

    public NullPredicate() {
        super("null?");
    }

    @Override
    protected void checkArgumentTypes(List<BudType> argumentTypes) {
        if (argumentTypes.size() != 1) {
            throw new EvaluatingException("requires 1 argument");
        }
    }

    @Override
    protected boolean predicate(List<BudObject> arguments) {
        BudObject argument = arguments.get(0);
        if (!BudType.Category.LIST.equals(argument.getType().getCategory())) {
            return false;
        }
        BudList list = (BudList) argument;
        return list.isNull();
    }
}
