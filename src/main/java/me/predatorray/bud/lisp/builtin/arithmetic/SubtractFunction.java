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

import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.math.BigDecimal;
import java.util.List;

public class SubtractFunction extends NumericArgumentFunction {

    public SubtractFunction() {
        super("-");
    }

    @Override
    protected BudObject applyNumbers(List<BudNumber> numbers) {
        if (numbers.isEmpty()) {
            return new BudNumber(BigDecimal.ZERO);
        }

        if (numbers.size() == 1) {
            BudNumber number = numbers.get(0);
            return new BudNumber(number.getValue().negate());
        }

        BigDecimal result = null;
        for (BudNumber argument : numbers) {
            BigDecimal number = argument.getValue();
            if (result == null) {
                result = number;
            } else {
                result = result.subtract(number);
            }
        }
        return new BudNumber(result);
    }

    @Override
    protected BudType checkArgumentSizeAndInspect(int size) {
        return BudType.NUMBER;
    }
}
