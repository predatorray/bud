package me.predatorray.bud.lisp.builtin;

import me.predatorray.bud.lisp.evaluator.ArgumentTypeMismatchException;
import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudList;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.ListType;

import java.util.List;

public class CarFunction extends NamedFunction {

    public CarFunction() {
        super("car");
    }

    @Override
    public BudType inspect(List<BudType> argumentTypes) {
        if (argumentTypes.size() != 1) {
            throw new EvaluatingException("requires 1 argument");
        }
        BudType argumentType = argumentTypes.get(0);
        if (argumentType.getCategory() != BudType.Category.LIST) {
            throw new ArgumentTypeMismatchException("expected arguments of type list, but " + argumentType);
        }
        ListType listType = (ListType) argumentType;
        return listType.getElementType();
    }

    @Override
    public BudObject apply(List<BudObject> arguments) {
        BudList list = (BudList) arguments.get(0);
        if (list.getSize() == 0) {
            throw new EvaluatingException("cannot car an empty list");
        }
        return list.getElements().get(0);
    }
}
