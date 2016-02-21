package me.predatorray.bud.lisp.buitin;

import me.predatorray.bud.lisp.evaluator.ArgumentTypeMismatchException;
import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudList;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.Function;
import me.predatorray.bud.lisp.lang.FunctionType;
import me.predatorray.bud.lisp.lang.ListType;
import me.predatorray.bud.lisp.lang.Symbol;

import java.util.List;

public class ConvertListElementTypeFunction implements Function {

    private final FunctionType thisType = new FunctionType(this);

    @Override
    public BudType inspect(List<BudType> argumentTypes) {
        if (!(argumentTypes.size() == 1 || argumentTypes.size() == 2)) {
            throw new EvaluatingException("requires 1 or 2 arguments");
        }
        if (!BudType.Category.LIST.equals(argumentTypes.get(0).getCategory())) {
            throw new ArgumentTypeMismatchException("expects a list of the first argument to be a list, but " +
                    argumentTypes.get(0));
        }
        if (argumentTypes.size() > 1) {
            BudType budType = argumentTypes.get(1);
            if (!BudType.SYMBOL.equals(budType)) {
                throw new ArgumentTypeMismatchException("expects the second argument to be a symbol, but " +
                        argumentTypes.get(1));
            }
        }
        return new ListType(null);
    }

    @Override
    public BudObject apply(List<BudObject> arguments) {
        BudList list = (BudList) arguments.get(0);
        BudType elementType = null;
        if (arguments.size() > 1) {
            Symbol symbolOfType = (Symbol) arguments.get(1);
            String typeName = symbolOfType.getName().toLowerCase();
            switch (typeName) {
                case "boolean":
                    elementType = BudType.BOOLEAN;
                    break;
                case "number":
                    elementType = BudType.NUMBER;
                    break;
                case "string":
                    elementType = BudType.STRING;
                    break;
                case "character":
                    elementType = BudType.CHARACTER;
                    break;
                default:
                    throw new EvaluatingException("unknown type " + typeName +
                            ", value must be one of (boolean/number/string/character)");
            }
        }

        return new BudList(elementType, list.getElements());
    }

    @Override
    public FunctionType getType() {
        return thisType;
    }
}
