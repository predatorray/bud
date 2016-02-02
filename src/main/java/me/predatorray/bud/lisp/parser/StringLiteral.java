package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.StringToken;

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
}
