package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.Token;

public class LambdaExpression extends TokenLocatedExpression {

    public LambdaExpression(Token locatedBy) {
        super(locatedBy);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
