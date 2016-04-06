package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.*;
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
    public BudObject evaluate(Environment environment, Evaluator evaluator) {
        BudObject applicable = evaluator.evaluate(operator, environment);
        List<BudObject> arguments = new ArrayList<>(operands.size());
        for (Expression operand : operands) {
            BudObject arg = evaluator.evaluate(operand, environment);
            arguments.add(arg);
        }

        return apply(applicable, arguments);
    }

    @Override
    public BudFuture evaluateAndGetBudFuture(Environment environment, Evaluator evaluator) {
        BudObject applicable = evaluator.evaluate(operator, environment);
        List<BudObject> arguments = new ArrayList<>(operands.size());
        for (Expression operand : operands) {
            BudObject arg = evaluator.evaluate(operand, environment);
            arguments.add(arg);
        }

        if (!BudType.Category.FUNCTION.equals(applicable.getType().getCategory())) {
            throw new EvaluatingException(applicable + " is not applicable");
        }
        Function function = (Function) applicable;

        List<BudType> argTypes = new ArrayList<>(arguments.size());
        for (BudObject argument : arguments) {
            argTypes.add(argument.getType());
        }
        function.inspect(argTypes);

        return new TailApplicationBudFuture(function, arguments);
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
