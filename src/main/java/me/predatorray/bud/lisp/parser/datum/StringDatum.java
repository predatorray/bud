package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.StringToken;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.StringLiteral;

public class StringDatum extends SimpleDatum<StringToken> {

    public StringDatum(StringToken stringToken) {
        super(stringToken);
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }

    @Override
    public Expression getExpression() {
        return new StringLiteral(token);
    }

    public String getValue() {
        return token.getStringValue();
    }
}
