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

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudNumber;

import java.math.BigDecimal;
import java.util.List;

public class MonoNonDecreasingPredicate extends NumericalPredicate {

    public MonoNonDecreasingPredicate() {
        super("<=");
    }

    @Override
    protected void checkArgumentSize(int size) {
        if (size < 2) {
            throw new EvaluatingException("requires at least 2 arguments");
        }
    }

    @Override
    protected boolean predicateNumbers(List<BudNumber> arguments) {
        BigDecimal prev = null;
        for (BudNumber argument : arguments) {
            BigDecimal value = argument.getValue();
            if (prev == null) {
                prev = value;
            } else if (prev.compareTo(value) > 0) {
                return false;
            } else {
                prev = value;
            }
        }
        return true;
    }
}
