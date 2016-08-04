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
import me.predatorray.bud.lisp.lang.*;
import me.predatorray.bud.lisp.lang.cont.Continuation;
import me.predatorray.bud.lisp.lang.cont.EvalExpressionCont;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.Validation;

public class IfSpecialForm extends CompoundExpression {

    private final Expression test;
    private final Expression consequent;
    private final Expression alternate;

    public IfSpecialForm(Expression test, Expression consequent, Expression alternate, LeftParenthesis leading) {
        super(leading, "if", test, consequent, alternate);
        this.test = Validation.notNull(test);
        this.consequent = Validation.notNull(consequent);
        this.alternate = Validation.notNull(alternate);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public Continuous evaluate(Environment environment, Evaluator evaluator) {
        BudObject tested = evaluator.evaluate(test, environment);
        if (BudBoolean.isTrue(tested)) {
            return new TailExpression(consequent, environment, evaluator);
        } else {
            return new TailExpression(alternate, environment, evaluator);
        }
    }

    @Override
    public Continuation evalCont(Environment environment, Evaluator evaluator) {
        return new IfCont(environment, evaluator);
    }

    private class IfCont implements Continuation {

        private final Environment environment;
        private final Evaluator evaluator;

        IfCont(Environment environment, Evaluator evaluator) {
            this.environment = environment;
            this.evaluator = evaluator;
        }

        @Override
        public BudObject run() {
            return evaluator.evaluate(test, environment);
        }

        @Override
        public Continuation handle(BudObject tested) {
            if (BudBoolean.isTrue(tested)) {
                return new EvalExpressionCont(consequent, environment, evaluator);
            } else {
                return new EvalExpressionCont(alternate, environment, evaluator);
            }
        }
    }

    public Expression getTest() {
        return test;
    }

    public Expression getConsequent() {
        return consequent;
    }

    public Expression getAlternate() {
        return alternate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IfSpecialForm that = (IfSpecialForm) o;

        if (!test.equals(that.test)) return false;
        if (!consequent.equals(that.consequent)) return false;
        return alternate.equals(that.alternate);
    }

    @Override
    public int hashCode() {
        int result = test.hashCode();
        result = 31 * result + consequent.hashCode();
        result = 31 * result + alternate.hashCode();
        return result;
    }
}
