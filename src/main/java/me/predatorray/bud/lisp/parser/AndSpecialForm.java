package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.Validation;

import java.util.List;

public class AndSpecialForm extends TokenLocatedExpression {

    private final List<Expression> tests;

    public AndSpecialForm(List<Expression> tests, LeftParenthesis leading) {
        super(leading);
        this.tests = Validation.notNull(tests);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    public List<Expression> getTests() {
        return tests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AndSpecialForm that = (AndSpecialForm) o;

        return tests.equals(that.tests);
    }

    @Override
    public int hashCode() {
        return tests.hashCode();
    }
}
