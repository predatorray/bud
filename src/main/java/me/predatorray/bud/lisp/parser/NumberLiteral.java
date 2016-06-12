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
package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.Continuous;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lang.Terminal;
import me.predatorray.bud.lisp.lexer.NumberToken;

import java.math.BigDecimal;

public class NumberLiteral extends TokenLocatedExpression {

    private final BigDecimal value;

    public NumberLiteral(NumberToken numberToken) {
        super(numberToken);
        this.value = numberToken.getValue();
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public Continuous evaluate(Environment environment, Evaluator evaluator) {
        return new Terminal(new BudNumber(value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberLiteral that = (NumberLiteral) o;

        return value.equals(that.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public BigDecimal getValue() {
        return value;
    }
}
