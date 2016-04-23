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

import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.util.Validation;

public final class TailExpressionBudFuture implements BudFuture {

    private final Expression tailExpression;
    private final Environment environment;
    private final Evaluator evaluator;

    public TailExpressionBudFuture(Expression tailExpression, Environment environment, Evaluator evaluator) {
        this.tailExpression = Validation.notNull(tailExpression);
        this.environment = Validation.notNull(environment);
        this.evaluator = Validation.notNull(evaluator);
    }

    @Override
    public BudObject getResult() {
        return null;
    }

    @Override
    public BudFuture getTailCall() {
        return tailExpression.evaluateAndGetBudFuture(environment, evaluator);
    }
}
