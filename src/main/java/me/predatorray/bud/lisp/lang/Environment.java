/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Wenhao Ji
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
