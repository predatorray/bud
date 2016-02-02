package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.BooleanToken;
import me.predatorray.bud.lisp.parser.BooleanLiteral;
import me.predatorray.bud.lisp.parser.Expression;

public class BooleanDatum implements Datum {

    private final BooleanToken booleanToken;

    public BooleanDatum(BooleanToken booleanToken) {
        this.booleanToken = booleanToken;
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }

    @Override
    public Expression getExpression() {
        return new BooleanLiteral(booleanToken);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooleanDatum that = (BooleanDatum) o;

        return booleanToken.equals(that.booleanToken);

    }

    @Override
    public int hashCode() {
        return booleanToken.hashCode();
    }
}
