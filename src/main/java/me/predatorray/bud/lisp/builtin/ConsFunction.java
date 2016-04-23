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
import me.predatorray.bud.lisp.lang.ListType;

import java.util.ArrayList;
import java.util.List;

public class ConsFunction extends NamedFunction {

    public ConsFunction() {
        super("cons");
    }

    @Override
    public BudType inspect(List<BudType> argumentTypes) {
        if (argumentTypes.size() != 2) {
            throw new EvaluatingException("requires 2 arguments");
        }
        BudType carType = argumentTypes.get(0);
        BudType cdrType = argumentTypes.get(1);
        return consType(carType, cdrType);
    }

    private ListType consType(BudType carType, BudType cdrType) {
        if (cdrType.getCategory() != BudType.Category.LIST) {
            throw new ArgumentTypeMismatchException("expects cdr argument to be a list, but " + cdrType);
        }
        BudType cdrElementType = ((ListType) cdrType).getElementType();
        if (carType != null && carType.equals(cdrElementType)) {
            return new ListType(carType);
        } else {
            return new ListType(null);
        }
    }

    @Override
    public BudObject apply(List<BudObject> arguments) {
        BudObject car = arguments.get(0);
        BudList cdr = ((BudList) arguments.get(1));
        List<BudObject> consElements = new ArrayList<>(cdr.getSize() + 1);
        consElements.add(car);
        consElements.addAll(cdr.getElements());
        ListType listType = consType(car.getType(), cdr.getType());
        return new BudList(listType.getElementType(), consElements);
    }
}
