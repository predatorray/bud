package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.parser.Expression;

public interface Datum {

    void accept(DatumVisitor datumVisitor);

    Expression getExpression();
}
