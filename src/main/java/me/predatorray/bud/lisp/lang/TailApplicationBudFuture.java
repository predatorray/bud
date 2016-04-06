package me.predatorray.bud.lisp.lang;

import me.predatorray.bud.lisp.util.Validation;

import java.util.List;

public final class TailApplicationBudFuture implements BudFuture {

    private final Function function;
    private final List<BudObject> arguments;

    public TailApplicationBudFuture(Function function, List<BudObject> arguments) {
        this.function = Validation.notNull(function);
        this.arguments = Validation.notNull(arguments);
    }

    @Override
    public BudObject getResult() {
        return null;
    }

    @Override
    public BudFuture getTailCall() {
        if (function instanceof TailCallFunction) {
            TailCallFunction tailCallFunction = (TailCallFunction) this.function;
            return tailCallFunction.applyAndGetBudFuture(arguments);
        } else {
            return new CompletedBudFuture(function.apply(arguments));
        }
    }
}
