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
import me.predatorray.bud.lisp.lang.MutableEnvironment;
import me.predatorray.bud.lisp.util.StringUtils;
import me.predatorray.bud.lisp.util.Validation;

import java.util.Collections;
import java.util.List;

public class Definition {

    private final Variable variable;
    private final Expression expression;
    private final List<Variable> formals;

    public Definition(Variable variable, Expression expression) {
        this.variable = Validation.notNull(variable);
        this.expression = Validation.notNull(expression);
        this.formals = null;
    }

    public Definition(Variable variable, List<Variable> formals, Expression expression) {
        this.variable = Validation.notNull(variable);
        this.expression = Validation.notNull(expression);
        this.formals = Validation.notNull(formals);
    }

    public void bind(MutableEnvironment environment, Evaluator evaluator) {
        if (formals == null) {
            // normal definition
            environment.bind(variable, evaluator.evaluate(expression, environment.toEnvironment()));
        } else {
            // named lambda definition
            LambdaExpression lambdaExpression = new LambdaExpression(formals, Collections.<Definition>emptyList(),
                    expression, variable, expression);
            environment.bind(variable, evaluator.evaluate(lambdaExpression, environment.toEnvironment()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Definition that = (Definition) o;

        if (!variable.equals(that.variable)) return false;
        if (!expression.equals(that.expression)) return false;
        return formals != null ? formals.equals(that.formals) : that.formals == null;

    }

    @Override
    public int hashCode() {
        int result = variable.hashCode();
        result = 31 * result + expression.hashCode();
        result = 31 * result + (formals != null ? formals.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(define ");
        if (formals == null) {
            sb.append(variable);
        } else {
            sb.append("(").append(variable).append(" ")
                    .append(StringUtils.join(formals, " "))
                    .append(")");
        }
        sb.append(" ").append(expression).append(")");
        return sb.toString();
    }

    public Variable getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }
}
