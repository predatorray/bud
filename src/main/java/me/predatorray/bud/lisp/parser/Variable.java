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

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.BudFuture;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.CompletedBudFuture;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lexer.IdentifierToken;
import me.predatorray.bud.lisp.util.Validation;

public class Variable extends TokenLocatedExpression {

    private final String variableName;

    public Variable(IdentifierToken identifierToken) {
        super(Validation.notNull(identifierToken, "identifierToken must not be null"));
        variableName = identifierToken.getName();
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public BudObject evaluate(Environment environment, Evaluator evaluator) {
        BudObject bound = environment.lookup(this);
        if (bound == null) {
            throw new EvaluatingException("unbound variable", this);
        }
        return bound;
    }

    @Override
    public BudFuture evaluateAndGetBudFuture(Environment environment, Evaluator evaluator) {
        return new CompletedBudFuture(evaluate(environment, evaluator));
    }

    public String getVariableName() {
        return variableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        return variableName.equals(variable.variableName);

    }

    @Override
    public String toString() {
        return variableName;
    }

    @Override
    public int hashCode() {
        return variableName.hashCode();
    }
}
