package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.TextLocation;

public interface Expression {

    TextLocation getLocation();

    void accept(ExpressionVisitor expressionVisitor);
}
