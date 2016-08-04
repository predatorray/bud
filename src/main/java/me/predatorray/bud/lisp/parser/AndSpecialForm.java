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
import me.predatorray.bud.lisp.lang.cont.Termination;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.Validation;

import java.util.List;

public class AndSpecialForm extends CompoundExpression {

    private final List<Expression> tests;

    public AndSpecialForm(List<Expression> tests, LeftParenthesis leading) {
        super(leading, "and", tests);
        this.tests = Validation.notNull(tests);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public Continuous evaluate(Environment environment, Evaluator evaluator) {
        if (tests.isEmpty()) {
            return new Terminal(BudBoolean.TRUE);
        }
        int last = tests.size() - 1;
        for (int i = 0; i < last; i++) {
            Expression test = tests.get(i);
            BudObject tested = evaluator.evaluate(test, environment);
            if (BudBoolean.isFalse(tested)) {
                return new Terminal(tested);
            }
        }

        return new TailExpression(tests.get(last), environment, evaluator);
    }

    @Override
    public Continuation evalCont(Environment environment, Evaluator evaluator) {
        return new AndCont(0, environment, evaluator);
    }

    private class AndCont implements Continuation {

        private final int index;
        private final Environment environment;
        private final Evaluator evaluator;

        AndCont(int index, Environment environment, Evaluator evaluator) {
            this.index = index;
            this.environment = environment;
            this.evaluator = evaluator;
        }

        @Override
        public BudObject run() {
            if (index >= tests.size()) {
                return BudBoolean.TRUE;
            }
            return evaluator.evaluate(tests.get(index), environment);
        }

        @Override
        public Continuation handle(BudObject eachTested) {
            if (index >= tests.size()) {
                return null;
            }
            if (BudBoolean.isFalse(eachTested)) {
                return null;
            }
            int next = index + 1;
            if (next == tests.size() - 1) {
                Expression nextTest = tests.get(next);
                return new EvalExpressionCont(nextTest, environment, evaluator);
            }
            return new AndCont(next, environment, evaluator);
        }
    }

    public List<Expression> getTests() {
        return tests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AndSpecialForm that = (AndSpecialForm) o;

        return tests.equals(that.tests);
    }

    @Override
    public int hashCode() {
        return tests.hashCode();
    }

    @Override
    public String toString() {
        return "";
    }
}
