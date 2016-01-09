package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.TextLocation;

public class SimpleDatum implements Datum {

    @Override
    public TextLocation getLocation() {
        return null;
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }
}
