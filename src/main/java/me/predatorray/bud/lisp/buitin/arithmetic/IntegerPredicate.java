package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.buitin.Predicate;
import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.FunctionType;

import java.math.BigDecimal;
import java.util.List;

public class IntegerPredicate extends Predicate {

    private final FunctionType thisType = new FunctionType(this);

    @Override
    protected void checkArgumentTypes(List<BudType> argumentTypes) {
        if (argumentTypes.size() != 1) {
            throw new EvaluatingException("requires one argument");
        }
    }

    @Override
    protected boolean predicate(List<BudObject> arguments) {
        BudObject arg = arguments.get(0);
        if (!BudType.NUMBER.equals(arg.getType())) {
            return false;
        }
        BudNumber number = (BudNumber) arg;
        BigDecimal decimal = number.getValue();
        return decimal.scale() <= 0;
    }

    @Override
    public FunctionType getType() {
        return thisType;
    }
}
