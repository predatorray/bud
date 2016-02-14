package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.TextLocation;
import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.util.Validation;

abstract class TokenLocatedExpression implements Expression {

    protected final TextLocation location;

    public TokenLocatedExpression(Token locatedBy) {
        this.location = Validation.notNull(locatedBy).getLocation();
    }

    public TokenLocatedExpression(Expression expression) {
        this.location = expression.getLocation();
    }

    @Override
    public final TextLocation getLocation() {
        return location;
    }
}
