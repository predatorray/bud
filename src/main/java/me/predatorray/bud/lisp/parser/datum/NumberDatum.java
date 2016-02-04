package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.NumberToken;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.NumberLiteral;

public class NumberDatum implements Datum {

    private final NumberToken numberToken;

    public NumberDatum(NumberToken numberToken) {
        this.numberToken = numberToken;
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberDatum that = (NumberDatum) o;

        return numberToken.equals(that.numberToken);
    }

    @Override
    public int hashCode() {
        return numberToken.hashCode();
    }

    @Override
    public Expression getExpression() {
        return new NumberLiteral(numberToken);
    }
}
