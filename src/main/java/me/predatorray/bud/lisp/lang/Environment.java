package me.predatorray.bud.lisp.lang;

import me.predatorray.bud.lisp.parser.Variable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Environment implements BudObject {

    private final Environment enclosed;
    private final Map<String, BudObject> bindings;
    private final String name;

    public Environment() {
        this(Collections.<Variable, BudObject>emptyMap(), null);
    }

    public Environment(Map<Variable, BudObject> bindings) {
        this(bindings, null);
    }

    public Environment(Map<Variable, BudObject> bindings, Environment enclosed) {
        this(toStringMap(bindings), enclosed, null);
    }

    Environment(Map<String, BudObject> bindings, Environment enclosed, String name) {
        if (bindings == null) {
            this.bindings = Collections.emptyMap();
        } else {
            this.bindings = Collections.unmodifiableMap(bindings);
        }
        this.enclosed = enclosed;
        this.name = name;
    }

    public BudObject lookup(Variable variable) {
        BudObject found = bindings.get(variable.getVariableName());
        if (found != null) {
            return found;
        } else if (enclosed != null) {
            return enclosed.lookup(variable);
        } else {
            return null;
        }
    }

    @Override
    public BudType getType() {
        return BudType.ENV;
    }

    public static Environment toEnvironment(Map<String, BudObject> bindings, String name) {
        return new Environment(bindings, null, name);
    }

    private static Map<String, BudObject> toStringMap(Map<Variable, BudObject> bindings) {
        Map<String, BudObject> stringMap = new HashMap<>(bindings.size());
        for (Map.Entry<Variable, BudObject> entry : bindings.entrySet()) {
            stringMap.put(entry.getKey().getVariableName(), entry.getValue());
        }
        return stringMap;
    }

    @Override
    public String toString() {
        return name == null ? "ENV@" + Integer.toHexString(hashCode()) : name;
    }
}
