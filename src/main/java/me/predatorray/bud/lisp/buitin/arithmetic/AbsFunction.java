package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.util.List;

public class AbsFunction extends NumericArgumentFunction {

    public AbsFunction() {
        super("abs");
    }

    @Override
    protected BudObject applyNumbers(List<BudNumber> numbers) {
        BudNumber n = numbers.get(0);
        return new BudNumber(n.getValue().abs());
    }

    @Override
    protected BudType checkArgumentSizeAndInspect(int size) {
        if (size != 1) {
            throw new EvaluatingException("requires 1 argument");
        }
        return BudType.NUMBER;
    }
}
