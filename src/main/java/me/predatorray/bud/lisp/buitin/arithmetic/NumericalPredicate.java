package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.buitin.Predicate;
import me.predatorray.bud.lisp.evaluator.ArgumentTypeMismatchException;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.FunctionType;

import java.util.ArrayList;
import java.util.List;

abstract class NumericalPredicate extends Predicate {

    private final FunctionType thisType = new FunctionType(this);

    @Override
    protected void checkArgumentTypes(List<BudType> argumentTypes) {
        int size = argumentTypes.size();
        checkArgumentSize(size);
        for (int i = 0; i < size; i++) {
            BudType argumentType = argumentTypes.get(i);
            if (!BudType.NUMBER.equals(argumentType)) {
                throw new ArgumentTypeMismatchException("expected arguments of type number, but the " +
                        (i + i) + "th argument is " + argumentType);
            }
        }
    }

    protected abstract void checkArgumentSize(int size);

    @Override
    protected boolean predicate(List<BudObject> arguments) {
        List<BudNumber> numbers = new ArrayList<>(arguments.size());
        for (BudObject argument : arguments) {
            numbers.add(((BudNumber) argument));
        }
        return predicateNumbers(numbers);
    }

    protected abstract boolean predicateNumbers(List<BudNumber> arguments);

    @Override
    public BudType getType() {
        return thisType;
    }
}
