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

import me.predatorray.bud.lisp.evaluator.ArgumentTypeMismatchException;
import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudList;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.util.List;

public class CdrFunction extends NamedFunction {

    public CdrFunction() {
        super("cdr");
    }

    @Override
    public BudType inspect(List<BudType> argumentTypes) {
        if (argumentTypes.size() != 1) {
            throw new EvaluatingException("requires 1 argument");
        }
        BudType argumentType = argumentTypes.get(0);
        if (argumentType.getCategory() != BudType.Category.LIST) {
            throw new ArgumentTypeMismatchException("expected arguments of type list, but " + argumentType);
        }
        return argumentType;
    }

    @Override
    public BudObject apply(List<BudObject> arguments) {
        BudList list = (BudList) arguments.get(0);
        if (list.getSize() == 0) {
            throw new EvaluatingException("cannot cdr an empty list");
        }
        List<BudObject> cdrElements = list.getElements().subList(1, list.getSize());
        return new BudList(list.getType().getElementType(), cdrElements);
    }
}
