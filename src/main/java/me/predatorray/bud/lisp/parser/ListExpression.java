package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.Validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListExpression extends TokenLocatedExpression {

    private final List<Expression> expressions;

    public ListExpression(List<? extends Expression> expressions, LeftParenthesis leading) {
        super(Validation.notNull(leading));
        this.expressions = Collections.unmodifiableList(new ArrayList<Expression>(Validation.notNull(expressions)));
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListExpression listDatum = (ListExpression) o;

        return expressions.equals(listDatum.expressions);

    }

    @Override
    public int hashCode() {
        return expressions.hashCode();
    }
}
