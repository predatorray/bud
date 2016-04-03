package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.buitin.OneArgumentPredicate;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.math.BigDecimal;

public class OddPredicate extends OneArgumentPredicate {

    private final BigDecimal TWO = new BigDecimal(2);

    public OddPredicate() {
        super("odd?");
    }

    @Override
    protected boolean predicate(BudObject argument) {
        if (!BudType.NUMBER.equals(argument.getType())) {
            return false;
        }
        BudNumber number = (BudNumber) argument;
        BigDecimal decimal = number.getValue();
        int scale = decimal.scale();
        if (scale > 0) {
            return false;
        } else {
            BigDecimal[] divideAndRemainder = decimal.divideAndRemainder(TWO);
            return !BigDecimal.ZERO.equals(divideAndRemainder[1]);
        }
    }
}