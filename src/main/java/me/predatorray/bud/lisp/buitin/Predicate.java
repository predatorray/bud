package me.predatorray.bud.lisp.buitin;

import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.Function;

import java.util.List;

public abstract class Predicate implements Function {

    @Override
    public final BudType inspect(List<BudType> argumentTypes) {
        checkArgumentTypes(argumentTypes);
        return BudType.BOOLEAN;
    }

    protected abstract void checkArgumentTypes(List<BudType> argumentTypes);

    @Override
    public final BudObject apply(List<BudObject> arguments) {
        return BudBoolean.valueOf(predicate(arguments));
    }

    protected abstract boolean predicate(List<BudObject> arguments);
}