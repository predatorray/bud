package me.predatorray.bud.lisp.builtin.arithmetic;

import me.predatorray.bud.lisp.builtin.OneArgumentPredicate;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

public class NegativePredicate extends OneArgumentPredicate {

    public NegativePredicate() {
        super("negative?");
    }

    @Override
    protected boolean predicate(BudObject argument) {
        if (!BudType.NUMBER.equals(argument.getType())) {
            return false;
        }
        BudNumber number = (BudNumber) argument;
        return number.getValue().signum() == -1;
    }
}
