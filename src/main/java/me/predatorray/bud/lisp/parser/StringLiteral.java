package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.StringToken;
import me.predatorray.bud.lisp.util.StringUtils;

public class StringLiteral extends TokenLocatedExpression {

    private final String value;

    public StringLiteral(StringToken stringToken) {
        super(stringToken);
        this.value = stringToken.getStringValue();
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringLiteral that = (StringLiteral) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return StringUtils.quote(value);
    }
}
