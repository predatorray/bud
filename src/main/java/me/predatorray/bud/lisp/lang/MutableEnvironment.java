package me.predatorray.bud.lisp.lang;

import me.predatorray.bud.lisp.parser.Variable;
import me.predatorray.bud.lisp.util.Validation;

import java.util.HashMap;
import java.util.Map;

public class MutableEnvironment {

    private final Environment enclosed;
    private Map<String, BudObject> bindings = new HashMap<String, BudObject>();

    public MutableEnvironment(Environment enclosed) {
        this.enclosed = enclosed;
    }

    public void bind(Variable variable, BudObject boundObject) {
        Validation.notNull(variable);
        Validation.notNull(boundObject);
        bindings.put(variable.getVariableName(), boundObject);
    }

    public Environment toEnvironment() {
        return new Environment(bindings, enclosed, new Object());
    }
}
