package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.StringToken;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.StringLiteral;

public class StringDatum implements Datum {

    private final StringToken stringToken;

    public StringDatum(StringToken stringToken) {
        this.stringToken = stringToken;
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringDatum that = (StringDatum) o;

        return stringToken.equals(that.stringToken);

    }

    @Override
    public int hashCode() {
        return stringToken.hashCode();
    }

    @Override
    public String toString() {
        return stringToken.toString();
    }

    @Override
    public Expression getExpression() {
        return new StringLiteral(stringToken);
    }

    public String getValue() {
        return stringToken.getStringValue();
    }
}
