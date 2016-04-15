package me.predatorray.bud.lisp.builtin;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.Environment;

import java.util.List;

public class NullEnvironmentFunction extends NamedFunction {

    public NullEnvironmentFunction() {
        super("null-environment");
    }

    @Override
    public BudType inspect(List<BudType> argumentTypes) {
        if (!argumentTypes.isEmpty()) {
            throw new EvaluatingException("no argument is required");
        }
        return BudType.ENV;
    }

    @Override
    public BudObject apply(List<BudObject> arguments) {
        return new Environment();
    }
}
