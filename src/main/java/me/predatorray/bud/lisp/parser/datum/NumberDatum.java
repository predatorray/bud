package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.NumberToken;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.NumberLiteral;

import java.math.BigDecimal;

public class NumberDatum extends SimpleDatum<NumberToken> {

    public NumberDatum(NumberToken numberToken) {
        super(numberToken);
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }

    @Override
    public Expression getExpression() {
        return new NumberLiteral(token);
    }

    public BigDecimal getValue() {
        return token.getValue();
    }
}
