package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.StringUtils;
import me.predatorray.bud.lisp.util.Validation;

import java.util.List;

public class LambdaExpression extends TokenLocatedExpression {

    private final List<Variable> formals;
    private final List<Definition> definitions;
    private final Expression bodyExpression;

    public LambdaExpression(List<Variable> formals, List<Definition> definitions, Expression bodyExpression,
                            LeftParenthesis leading) {
        super(leading);
        this.formals = Validation.notNull(formals, "formals must not be empty");
        this.definitions = Validation.notNull(definitions);
        this.bodyExpression = Validation.notNull(bodyExpression);
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
        return bodyExpression.equals(that.bodyExpression);
    }

    @Override
    public int hashCode() {
        int result = formals.hashCode();
        result = 31 * result + definitions.hashCode();
        result = 31 * result + bodyExpression.hashCode();
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
