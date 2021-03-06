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
import me.predatorray.bud.lisp.lang.Symbol;

import java.util.List;

public class ConvertListElementTypeFunction extends NamedFunction {

    public ConvertListElementTypeFunction() {
        super("list->typed-list");
    }

    @Override
    public BudType inspect(List<BudType> argumentTypes) {
        if (!(argumentTypes.size() == 1 || argumentTypes.size() == 2)) {
            throw new EvaluatingException("requires 1 or 2 arguments");
        }
        if (!BudType.Category.LIST.equals(argumentTypes.get(0).getCategory())) {
            throw new ArgumentTypeMismatchException("expects a list of the first argument to be a list, but " +
                    argumentTypes.get(0));
        }
        if (argumentTypes.size() > 1) {
            BudType budType = argumentTypes.get(1);
            if (!BudType.SYMBOL.equals(budType)) {
                throw new ArgumentTypeMismatchException("expects the second argument to be a symbol, but " +
                        argumentTypes.get(1));
            }
        }
        return new ListType(null);
    }

    @Override
    public BudObject apply(List<BudObject> arguments) {
        BudList list = (BudList) arguments.get(0);
        BudType elementType = null;
        if (arguments.size() > 1) {
            Symbol symbolOfType = (Symbol) arguments.get(1);
            String typeName = symbolOfType.getName().toLowerCase();
            switch (typeName) {
                case "boolean":
                    elementType = BudType.BOOLEAN;
                    break;
                case "number":
                    elementType = BudType.NUMBER;
                    break;
                case "string":
                    elementType = BudType.STRING;
                    break;
                case "character":
                    elementType = BudType.CHARACTER;
                    break;
                default:
                    throw new EvaluatingException("unknown type " + typeName +
                            ", value must be one of (boolean/number/string/character)");
            }
        }

        return new BudList(elementType, list.getElements());
    }
}
