package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.util.Collections;
import java.util.List;

public class MaxFunction extends NumericArgumentFunction {

    public MaxFunction() {
        super("max");
    }

    @Override
    protected BudType checkArgumentSizeAndInspect(int size) {
        if (size == 0) {
            throw new EvaluatingException("requires at least 1 argument");
        }
        return BudType.NUMBER;
    }

    @Override
    protected BudObject applyNumbers(List<BudNumber> numbers) {
        return Collections.max(numbers);
    }
}
