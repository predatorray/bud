package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.buitin.NamedFunction;
import me.predatorray.bud.lisp.evaluator.ArgumentTypeMismatchException;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.util.ArrayList;
import java.util.List;

public abstract class NumericArgumentFunction extends NamedFunction {

    protected abstract BudObject applyNumbers(List<BudNumber> numbers);

    protected abstract BudType checkArgumentSizeAndInspect(int size);

    public NumericArgumentFunction(String ...names) {
        super(names);
    }

    @Override
    public final BudType inspect(List<BudType> argumentTypes) {
        int size = argumentTypes.size();
        for (int i = 0; i < size; i++) {
            BudType argumentType = argumentTypes.get(i);
            if (!BudType.NUMBER.equals(argumentType)) {
                throw new ArgumentTypeMismatchException("expected arguments of type number, but the " +
                        (i + i) + "th argument is " + argumentType);
            }
        }
        return checkArgumentSizeAndInspect(size);
    }

    @Override
    public final BudObject apply(List<BudObject> arguments) {
        List<BudNumber> numbers = new ArrayList<>(arguments.size());
        for (BudObject argument : arguments) {
            numbers.add(((BudNumber) argument));
        }
        return applyNumbers(numbers);
    }
}
