package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.BooleanToken;
import me.predatorray.bud.lisp.parser.BooleanLiteral;
import me.predatorray.bud.lisp.parser.Expression;

public class BooleanDatum extends SimpleDatum<BooleanToken> {

    public BooleanDatum(BooleanToken booleanToken) {
        super(booleanToken);
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }

    @Override
    public Expression getExpression() {
        return new BooleanLiteral(token);
    }

    public boolean getValue() {
        return token.getValue();
    }
}
