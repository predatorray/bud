package me.predatorray.bud.lisp.buitin;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lang.Function;
import me.predatorray.bud.lisp.lang.FunctionType;

import java.util.List;

public class NullEnvironmentFunction implements Function {

    private final FunctionType thisType = new FunctionType(this);

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

    @Override
    public BudType getType() {
        return thisType;
    }
}
