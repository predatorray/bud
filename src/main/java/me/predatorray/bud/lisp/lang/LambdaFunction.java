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

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.parser.Definition;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.LambdaExpression;
import me.predatorray.bud.lisp.parser.Variable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LambdaFunction implements Function, TailCallFunction {

    private final FunctionType thisType = new FunctionType(this);
    private final List<Variable> formals;
    private final List<Definition> definitions;
    private final Expression body;

    private final LambdaExpression lambdaExpression;
    private final Environment lexicalEnv;
    private final Variable self;

    private final Evaluator evaluator;

    public LambdaFunction(LambdaExpression lambdaExpression, Environment lexicalEnv, Variable self,
                          Evaluator evaluator) {
        this.lambdaExpression = lambdaExpression;
        this.lexicalEnv = lexicalEnv;
        this.self = self;
        this.evaluator = evaluator;
        this.formals = lambdaExpression.getFormals();
        this.definitions = lambdaExpression.getDefinitions();
        this.body = lambdaExpression.getBodyExpression();
    }

    @Override
    public BudType inspect(List<BudType> argumentTypes) {
        return null; // TODO
    }

    @Override
    public BudObject apply(List<BudObject> arguments) {
        Environment enclosing = generateEnvForBody(arguments);
        return evaluator.evaluate(body, enclosing);
    }

    @Override
    public Continuous applyAndGetBudFuture(List<BudObject> arguments) {
        Environment enclosing = generateEnvForBody(arguments);
        return new TailExpression(body, enclosing, evaluator);
    }

    private Environment generateEnvForBody(List<BudObject> arguments) {
        int requires = formals.size();
        int actualSize = arguments.size();
        if (requires != actualSize) {
            String msg = "requires " + requires + " arguments, but " + actualSize + " actually";
            throw new EvaluatingException(msg, lambdaExpression);
        }

        Map<Variable, BudObject> argumentBindings = new HashMap<>(actualSize);
        for (int i = 0; i < actualSize; i++) {
            Variable formal = formals.get(i);
            BudObject actual = arguments.get(i);
            argumentBindings.put(formal, actual);
        }

        MutableEnvironment envDefined = new MutableEnvironment(lexicalEnv);
        if (self != null) {
            envDefined.bind(self, this);
        }
        for (Definition definition : definitions) {
            definition.bind(envDefined, evaluator);
        }
        return new Environment(argumentBindings, envDefined.toEnvironment());
    }

    @Override
    public BudType getType() {
        return thisType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LambdaFunction that = (LambdaFunction) o;

        if (!lambdaExpression.equals(that.lambdaExpression)) return false;
        if (!lexicalEnv.equals(that.lexicalEnv)) return false;
        return self != null ? self.equals(that.self) : that.self == null;
    }

    @Override
    public int hashCode() {
        int result = lambdaExpression.hashCode();
        result = 31 * result + lexicalEnv.hashCode();
        result = 31 * result + (self != null ? self.hashCode() : 0);
        return result;
    }
}
