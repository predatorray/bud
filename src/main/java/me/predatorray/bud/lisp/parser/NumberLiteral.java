package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.NumberToken;

public class NumberLiteral extends TokenLocatedExpression {

    public NumberLiteral(NumberToken numberToken) {
        super(numberToken);
        // TODO
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
