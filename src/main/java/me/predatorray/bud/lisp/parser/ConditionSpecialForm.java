package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.Validation;

import java.util.List;

public class ConditionSpecialForm extends CompoundExpression {

    private final List<ConditionClause> clauses;
    private final Expression elseExpression;

    public ConditionSpecialForm(List<ConditionClause> clauses, Expression elseExpression, LeftParenthesis leading) {
        super(leading, "cond", clauses);
        this.clauses = Validation.notEmpty(clauses);
        this.elseExpression = elseExpression;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConditionSpecialForm that = (ConditionSpecialForm) o;

        if (!clauses.equals(that.clauses)) return false;
        return elseExpression != null ? elseExpression.equals(that.elseExpression) : that.elseExpression == null;
    }

    @Override
    public int hashCode() {
        int result = clauses.hashCode();
        result = 31 * result + (elseExpression != null ? elseExpression.hashCode() : 0);
        return result;
    }

    public List<ConditionClause> getClauses() {
        return clauses;
    }

    public Expression getElseExpression() {
        return elseExpression;
    }
}
