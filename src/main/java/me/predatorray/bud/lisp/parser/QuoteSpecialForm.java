package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.parser.datum.Datum;
import me.predatorray.bud.lisp.util.Validation;

public class QuoteSpecialForm extends TokenLocatedExpression {

    private final Datum quotedDatum;

    public QuoteSpecialForm(Datum quotedDatum, LeftParenthesis leading) {
        super(leading);
        this.quotedDatum = Validation.notNull(quotedDatum);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    public Datum getQuotedDatum() {
        return quotedDatum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuoteSpecialForm that = (QuoteSpecialForm) o;

        return quotedDatum.equals(that.quotedDatum);
    }

    @Override
    public int hashCode() {
        return quotedDatum.hashCode();
    }
}
