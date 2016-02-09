package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.Validation;

public class IfSpecialForm extends TokenLocatedExpression {

    private final Expression test;
    private final Expression consequent;
    private final Expression alternate;

    public IfSpecialForm(Expression test, Expression consequent, Expression alternate, LeftParenthesis leading) {
        super(leading);
        this.test = Validation.notNull(test);
        this.consequent = Validation.notNull(consequent);
        this.alternate = Validation.notNull(alternate);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    public Expression getTest() {
        return test;
    }

    public Expression getConsequent() {
        return consequent;
    }

    public Expression getAlternate() {
        return alternate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IfSpecialForm that = (IfSpecialForm) o;

        if (!test.equals(that.test)) return false;
        if (!consequent.equals(that.consequent)) return false;
        return alternate.equals(that.alternate);
    }

    @Override
    public int hashCode() {
        int result = test.hashCode();
        result = 31 * result + consequent.hashCode();
        result = 31 * result + alternate.hashCode();
        return result;
    }
}
