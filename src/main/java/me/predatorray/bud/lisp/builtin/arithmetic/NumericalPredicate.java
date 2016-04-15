package me.predatorray.bud.lisp.builtin.arithmetic;

import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.util.List;

abstract class NumericalPredicate extends NumericArgumentFunction {

    public NumericalPredicate(String ...names) {
        super(names);
    }

    protected abstract boolean predicateNumbers(List<BudNumber> arguments);

    protected abstract void checkArgumentSize(int size);

    @Override
    protected final BudObject applyNumbers(List<BudNumber> numbers) {
        return BudBoolean.valueOf(predicateNumbers(numbers));
    }

    @Override
    protected final BudType checkArgumentSizeAndInspect(int size) {
        checkArgumentSize(size);
        return BudType.BOOLEAN;
    }
}
