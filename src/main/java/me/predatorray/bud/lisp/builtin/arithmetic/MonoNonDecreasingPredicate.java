package me.predatorray.bud.lisp.builtin.arithmetic;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudNumber;

import java.math.BigDecimal;
import java.util.List;

public class MonoNonDecreasingPredicate extends NumericalPredicate {

    public MonoNonDecreasingPredicate() {
        super("<=");
    }

    @Override
    protected void checkArgumentSize(int size) {
        if (size < 2) {
            throw new EvaluatingException("requires at least 2 arguments");
        }
    }

    @Override
    protected boolean predicateNumbers(List<BudNumber> arguments) {
        BigDecimal prev = null;
        for (BudNumber argument : arguments) {
            BigDecimal value = argument.getValue();
            if (prev == null) {
                prev = value;
            } else if (prev.compareTo(value) > 0) {
                return false;
            } else {
                prev = value;
            }
        }
        return true;
    }
}
