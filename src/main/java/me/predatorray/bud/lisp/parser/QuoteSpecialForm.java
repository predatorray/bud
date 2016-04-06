package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.*;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.parser.datum.*;
import me.predatorray.bud.lisp.util.Validation;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public BudObject evaluate(Environment environment, Evaluator evaluator) {
        DatumObjectConstructor constructor = new DatumObjectConstructor();
        quotedDatum.accept(constructor);
        return constructor.datumObject;
    }

    @Override
    public BudFuture evaluateAndGetBudFuture(Environment environment, Evaluator evaluator) {
        return new CompletedBudFuture(evaluate(environment, evaluator));
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

    @Override
    public String toString() {
        return "(quote " + quotedDatum + ")";
    }

    private class DatumObjectConstructor implements DatumVisitor {

        private BudObject datumObject;

        @Override
        public void visit(BooleanDatum booleanDatum) {
            datumObject = BudBoolean.valueOf(booleanDatum.getValue());
        }

        @Override
        public void visit(NumberDatum numberDatum) {
            datumObject = new BudNumber(numberDatum.getValue());
        }

        @Override
        public void visit(StringDatum stringDatum) {
            datumObject = new BudString(stringDatum.getValue());
        }

        @Override
        public void visit(CharacterDatum characterDatum) {
            datumObject = new BudCharacter(characterDatum.getValue());
        }

        @Override
        public void visit(SymbolDatum symbolDatum) {
            datumObject = new Symbol(symbolDatum.getName());
        }

        @Override
        public void visit(CompoundDatum compoundDatum) {
            List<Datum> data = compoundDatum.getData();
            List<BudObject> objects = new ArrayList<>(data.size());
            for (Datum datum : data) {
                DatumObjectConstructor constructor = new DatumObjectConstructor();
                datum.accept(constructor);
                objects.add(constructor.datumObject);
            }
            datumObject = new BudList(null, objects);
        }
    }
}
