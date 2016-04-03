package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudNumber;

import java.util.List;

public class NumberEqualPredicate extends NumericalPredicate {

    public NumberEqualPredicate() {
        super("=");
    }

    @Override
    protected void checkArgumentSize(int size) {
        if (size < 2) {
            throw new EvaluatingException("requires at least 2 arguments");
        }
    }

    @Override
    protected boolean predicateNumbers(List<BudNumber> arguments) {
        BudNumber base = arguments.get(0);
        for (int i = 1; i < arguments.size(); i++) {
            if (!base.equals(arguments.get(i))) {
                return false;
            }
        }
        return true;
    }
}
