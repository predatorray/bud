package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.TextLocation;

public interface Datum {

    TextLocation getLocation();

    void accept(DatumVisitor datumVisitor);
}
