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
import me.predatorray.bud.lisp.lang.Continuous;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lang.LambdaFunction;
import me.predatorray.bud.lisp.lang.Terminal;
import me.predatorray.bud.lisp.lang.cont.Continuation;
import me.predatorray.bud.lisp.lang.cont.Termination;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.StringUtils;
import me.predatorray.bud.lisp.util.Validation;

import java.util.List;

public class LambdaExpression extends TokenLocatedExpression {

    private final List<Variable> formals;
    private final List<Definition> definitions;
    private final Expression bodyExpression;
    private final Variable self;

    public LambdaExpression(List<Variable> formals, List<Definition> definitions, Expression bodyExpression,
                            LeftParenthesis leading) {
        super(leading);
        this.formals = Validation.notNull(formals, "formals must not be empty");
        this.definitions = Validation.notNull(definitions);
        this.bodyExpression = Validation.notNull(bodyExpression);
        this.self = null;
    }

    public LambdaExpression(List<Variable> formals, List<Definition> definitions, Expression bodyExpression,
                            Variable self, Expression locatedBy) {
        super(locatedBy);
        this.formals = Validation.notNull(formals, "formals must not be empty");
        this.definitions = Validation.notNull(definitions);
        this.bodyExpression = Validation.notNull(bodyExpression);
        this.self = self;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public Continuous evaluate(Environment environment, Evaluator evaluator) {
        return new Terminal(new LambdaFunction(this, environment, self, evaluator));
    }

    @Override
    public Continuation evalCont(Environment environment, Evaluator evaluator) {
        return new Termination(new LambdaFunction(this, environment, self, evaluator));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LambdaExpression that = (LambdaExpression) o;

        if (!formals.equals(that.formals)) return false;
        if (!definitions.equals(that.definitions)) return false;
        if (!bodyExpression.equals(that.bodyExpression)) return false;
        return self != null ? self.equals(that.self) : that.self == null;
    }

    @Override
    public int hashCode() {
        int result = formals.hashCode();
        result = 31 * result + definitions.hashCode();
        result = 31 * result + bodyExpression.hashCode();
        result = 31 * result + (self != null ? self.hashCode() : 0);
        return result;
    }

    public List<Variable> getFormals() {
        return formals;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public Expression getBodyExpression() {
        return bodyExpression;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(lambda (");
        sb.append(StringUtils.join(formals, " ")).append(")");
        if (!definitions.isEmpty()) {
            sb.append(" ").append(StringUtils.join(definitions, " "));
        }
        sb.append(" ").append(bodyExpression).append(")");
        return sb.toString();
    }
}
