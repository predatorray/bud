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
package me.predatorray.bud.lisp.builtin.arithmetic;

import me.predatorray.bud.lisp.builtin.NamedFunction;
import me.predatorray.bud.lisp.evaluator.ArgumentTypeMismatchException;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.util.ArrayList;
import java.util.List;

public abstract class NumericArgumentFunction extends NamedFunction {

    protected abstract BudObject applyNumbers(List<BudNumber> numbers);

    protected abstract BudType checkArgumentSizeAndInspect(int size);

    public NumericArgumentFunction(String ...names) {
        super(names);
    }

    @Override
    public final BudType inspect(List<BudType> argumentTypes) {
        int size = argumentTypes.size();
        for (int i = 0; i < size; i++) {
            BudType argumentType = argumentTypes.get(i);
            if (!BudType.NUMBER.equals(argumentType)) {
                throw new ArgumentTypeMismatchException("expected arguments of type number, but the " +
                        (i + i) + "th argument is " + argumentType);
            }
        }
        return checkArgumentSizeAndInspect(size);
    }

    @Override
    public final BudObject apply(List<BudObject> arguments) {
        List<BudNumber> numbers = new ArrayList<>(arguments.size());
        for (BudObject argument : arguments) {
            numbers.add(((BudNumber) argument));
        }
        return applyNumbers(numbers);
    }
}
