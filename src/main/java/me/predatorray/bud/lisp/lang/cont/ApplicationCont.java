package me.predatorray.bud.lisp.lang.cont;

import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Function;
import me.predatorray.bud.lisp.lang.TailCallFunction;

import java.util.List;

public class ApplicationCont implements Continuation {

    private final Function function;
    private final List<BudObject> arguments;

    public ApplicationCont(Function function, List<BudObject> arguments) {
        this.function = function;
        this.arguments = arguments;
    }

    @Override
    public BudObject run() {
        return null;
    }

    @Override
    public Continuation handle(BudObject ignored) {
        if (function instanceof TailCallFunction) {
            TailCallFunction tailCallFunction = (TailCallFunction) this.function;
            return new ContinuousCont(tailCallFunction.applyAndGetBudFuture(arguments)); // TODO
        } else {
            return new Termination(function.apply(arguments));
        }
    }
}
