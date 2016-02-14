package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.FunctionType;

import java.util.List;

public class NumberEqualPredicate extends NumericalPredicate {

    private final FunctionType thisType = new FunctionType(this);

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

    @Override
    public BudType getType() {
        return thisType;
    }
}
