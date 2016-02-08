package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.Validation;

public class Definition extends TokenLocatedExpression {

    private final Variable variable;
    private final Expression expression;

    public Definition(Variable variable, Expression expression, LeftParenthesis leading) {
        super(leading);
        this.variable = Validation.notNull(variable);
        this.expression = Validation.notNull(expression);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Definition that = (Definition) o;

        if (!variable.equals(that.variable)) return false;
        return expression.equals(that.expression);
    }

    @Override
    public int hashCode() {
        int result = variable.hashCode();
        result = 31 * result + expression.hashCode();
        return result;
    }

    public Variable getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }
}
