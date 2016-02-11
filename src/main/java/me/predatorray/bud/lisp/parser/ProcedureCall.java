package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lang.Function;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;

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
    public BudObject evaluate(Environment environment) {
        BudObject applicable = operator.evaluate(environment);
        if (!BudType.Category.FUNCTION.equals(applicable.getType().getCategory())) {
            throw new EvaluatingException("not applicable", operator);
        }
        Function function = (Function) applicable;

        List<BudType> argTypes = new ArrayList<>(operands.size());
        List<BudObject> arguments = new ArrayList<>(operands.size());
        for (Expression operand : operands) {
            BudObject arg = operand.evaluate(environment);
            argTypes.add(arg.getType());
            arguments.add(arg);
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
