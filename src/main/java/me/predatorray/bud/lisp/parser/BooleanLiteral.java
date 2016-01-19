package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.BooleanToken;

public class BooleanLiteral extends TokenLocatedDatum {

    private final boolean value;

    public BooleanLiteral(BooleanToken booleanToken) {
        super(booleanToken);
        this.value = booleanToken.getValue();
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }

    public boolean getValue() {
        return value;
    }
}
