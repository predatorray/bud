package me.predatorray.bud.lisp.buitin;

import me.predatorray.bud.lisp.evaluator.ArgumentTypeMismatchException;
import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudList;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.Function;
import me.predatorray.bud.lisp.lang.FunctionType;

import java.util.List;

public class CdrFunction implements Function {

    private final FunctionType thisType = new FunctionType(this);

    @Override
    public BudType inspect(List<BudType> argumentTypes) {
        if (argumentTypes.size() != 1) {
            throw new EvaluatingException("requires 1 argument");
        }
        BudType argumentType = argumentTypes.get(0);
        if (argumentType.getCategory() != BudType.Category.LIST) {
            throw new ArgumentTypeMismatchException("expected arguments of type list, but " + argumentType);
        }
        return argumentType;
    }

    @Override
    public BudObject apply(List<BudObject> arguments) {
        BudList list = (BudList) arguments.get(0);
        if (list.getSize() == 0) {
            throw new EvaluatingException("cannot cdr an empty list");
        }
        List<BudObject> cdrElements = list.getElements().subList(1, list.getSize());
        return new BudList(list.getType().getElementType(), cdrElements);
    }

    @Override
    public BudType getType() {
        return thisType;
    }
}
