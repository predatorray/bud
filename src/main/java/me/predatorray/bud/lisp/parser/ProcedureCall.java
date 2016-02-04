package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;

import java.util.List;

public class ProcedureCall extends TokenLocatedExpression {

    private final Expression operator;
    private final List<? extends Expression> operands;

    public ProcedureCall(Expression operator, List<? extends Expression> operands, LeftParenthesis leading) {
        super(leading);
        this.operator = operator;
        this.operands = operands;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
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
}
