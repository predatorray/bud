package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.TextLocation;
import me.predatorray.bud.lisp.lexer.Token;

abstract class TokenLocatedDatum implements Datum {

    protected final TextLocation location;

    public TokenLocatedDatum(Token locatedBy) {
        this.location = locatedBy.getLocation();
    }

    @Override
    public final TextLocation getLocation() {
        return location;
    }
}
