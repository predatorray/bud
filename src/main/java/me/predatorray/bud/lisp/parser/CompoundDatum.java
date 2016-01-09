package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.TextLocation;

public class CompoundDatum implements Datum {

    @Override
    public TextLocation getLocation() {
        return null;
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }
}
