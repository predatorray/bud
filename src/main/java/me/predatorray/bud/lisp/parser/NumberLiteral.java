package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.NumberToken;

public class NumberLiteral extends TokenLocatedDatum {

    public NumberLiteral(NumberToken numberToken) {
        super(numberToken);
        // TODO
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }
}
