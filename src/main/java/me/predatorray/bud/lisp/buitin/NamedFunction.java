package me.predatorray.bud.lisp.buitin;

import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Function;
import me.predatorray.bud.lisp.lang.FunctionType;
import me.predatorray.bud.lisp.util.Validation;

import java.util.Map;

public abstract class NamedFunction implements Function {

    private final FunctionType thisType = new FunctionType(this);
    private final String[] names;

    public NamedFunction(String ...names) {
        this.names = Validation.notEmpty(names);
        for (String name : this.names) {
            Validation.notEmpty(name);
        }
    }

    public final void register(Map<String, BudObject> bindings) {
        for (String name : names) {
            BudObject duplicate = bindings.put(name, this);
            if (duplicate != null) {
                throw new IllegalStateException(String.format("duplicate bindings: [%s -> %s] and [%s -> %s]",
                        name, duplicate, name, this));
            }
        }
    }

    @Override
    public FunctionType getType() {
        return thisType;
    }
}
