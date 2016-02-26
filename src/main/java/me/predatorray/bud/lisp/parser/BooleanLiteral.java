package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lexer.BooleanToken;

public class BooleanLiteral extends TokenLocatedExpression {

    private final boolean value;

    public BooleanLiteral(BooleanToken booleanToken) {
        super(booleanToken);
        this.value = booleanToken.getValue();
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public BudObject evaluate(Environment environment, Evaluator evaluator) {
        return BudBoolean.valueOf(value);
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value ? "#t" : "#f";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooleanLiteral that = (BooleanLiteral) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (value ? 1 : 0);
    }
}
