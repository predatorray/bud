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
package me.predatorray.bud.lisp.builtin;

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
