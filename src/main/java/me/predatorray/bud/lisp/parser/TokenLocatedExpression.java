package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.TextLocation;
import me.predatorray.bud.lisp.lexer.Token;

abstract class TokenLocatedExpression implements Expression {

    protected final TextLocation location;

    public TokenLocatedExpression(Token locatedBy) {
        this.location = locatedBy.getLocation();
    }

    @Override
    public final TextLocation getLocation() {
        return location;
    }
}
