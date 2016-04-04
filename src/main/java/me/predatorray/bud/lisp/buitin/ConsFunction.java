package me.predatorray.bud.lisp.buitin;

import me.predatorray.bud.lisp.evaluator.ArgumentTypeMismatchException;
import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudList;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.ListType;

import java.util.ArrayList;
import java.util.List;

public class ConsFunction extends NamedFunction {

    public ConsFunction() {
        super("cons");
    }

    @Override
    public BudType inspect(List<BudType> argumentTypes) {
        if (argumentTypes.size() != 2) {
            throw new EvaluatingException("requires 2 arguments");
        }
        BudType carType = argumentTypes.get(0);
        BudType cdrType = argumentTypes.get(1);
        return consType(carType, cdrType);
    }

    private ListType consType(BudType carType, BudType cdrType) {
        if (cdrType.getCategory() != BudType.Category.LIST) {
            throw new ArgumentTypeMismatchException("expects cdr argument to be a list, but " + cdrType);
        }
        BudType cdrElementType = ((ListType) cdrType).getElementType();
        if (carType != null && carType.equals(cdrElementType)) {
            return new ListType(carType);
        } else {
            return new ListType(null);
        }
    }

    @Override
    public BudObject apply(List<BudObject> arguments) {
        BudObject car = arguments.get(0);
        BudList cdr = ((BudList) arguments.get(1));
        List<BudObject> consElements = new ArrayList<>(cdr.getSize() + 1);
        consElements.add(car);
        consElements.addAll(cdr.getElements());
        ListType listType = consType(car.getType(), cdr.getType());
        return new BudList(listType.getElementType(), consElements);
    }
}
