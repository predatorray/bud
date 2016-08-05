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
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.Continuous;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lang.Function;
import me.predatorray.bud.lisp.lang.TailApplication;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.Validation;

import java.util.ArrayList;
import java.util.List;

public class ProcedureCall extends CompoundExpression {

    private final Expression operator;
    private final List<? extends Expression> operands;

    public ProcedureCall(Expression operator, List<? extends Expression> operands, LeftParenthesis leading) {
        super(leading, operator.toString(), operands);
        this.operator = operator;
        this.operands = operands;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public Continuous evaluate(Environment environment, Evaluator evaluator) {
        BudObject applicable = evaluator.evaluate(operator, environment);
        List<BudObject> arguments = new ArrayList<>(operands.size());
        for (Expression operand : operands) {
            BudObject arg = evaluator.evaluate(operand, environment);
            arguments.add(arg);
        }
        return new TailApplication(applicable, arguments);
    }

    public static BudObject apply(BudObject applicable, List<BudObject> arguments) {
        Validation.notNull(applicable);
        Validation.notNull(arguments);

        if (!BudType.Category.FUNCTION.equals(applicable.getType().getCategory())) {
            throw new EvaluatingException(applicable + " is not applicable");
        }
        Function function = (Function) applicable;

        List<BudType> argTypes = new ArrayList<>(arguments.size());
        for (BudObject argument : arguments) {
            argTypes.add(argument.getType());
        }
        function.inspect(argTypes);
        return function.apply(arguments);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcedureCall that = (ProcedureCall) o;

        if (!operator.equals(that.operator)) return false;
        return operands.equals(that.operands);

    }

    @Override
    public int hashCode() {
        int result = operator.hashCode();
        result = 31 * result + operands.hashCode();
        return result;
    }

    public Expression getOperator() {
        return operator;
    }

    public List<? extends Expression> getOperands() {
        return operands;
    }
}
