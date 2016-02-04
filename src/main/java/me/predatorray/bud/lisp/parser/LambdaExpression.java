package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.Validation;

import java.util.List;

public class LambdaExpression extends TokenLocatedExpression {

    private final List<Variable> formals;
    private final List<Definition> definitions;
    private final Expression lambdaExpression;

    public LambdaExpression(List<Variable> formals, List<Definition> definitions, Expression lambdaExpression,
                            LeftParenthesis leading) {
        super(leading);
        this.formals = Validation.notEmpty(formals, "formals must not be empty");
        this.definitions = Validation.notNull(definitions);
        this.lambdaExpression = Validation.notNull(lambdaExpression);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LambdaExpression that = (LambdaExpression) o;

        if (!formals.equals(that.formals)) return false;
        if (!definitions.equals(that.definitions)) return false;
        return lambdaExpression.equals(that.lambdaExpression);
    }

    @Override
    public int hashCode() {
        int result = formals.hashCode();
        result = 31 * result + definitions.hashCode();
        result = 31 * result + lambdaExpression.hashCode();
        return result;
    }
}
