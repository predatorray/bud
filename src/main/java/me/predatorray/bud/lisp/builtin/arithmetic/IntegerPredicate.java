package me.predatorray.bud.lisp.builtin.arithmetic;

import me.predatorray.bud.lisp.builtin.OneArgumentPredicate;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.math.BigDecimal;

public class IntegerPredicate extends OneArgumentPredicate {

    public IntegerPredicate() {
        super("integer?");
    }

    @Override
    protected boolean predicate(BudObject argument) {
        if (!BudType.NUMBER.equals(argument.getType())) {
            return false;
        }
        BudNumber number = (BudNumber) argument;
        BigDecimal decimal = number.getValue();
        return decimal.scale() <= 0;
    }
}
