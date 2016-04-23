/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Wenhao Ji
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudCharacter;
import me.predatorray.bud.lisp.lang.BudFuture;
import me.predatorray.bud.lisp.lang.BudList;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudString;
import me.predatorray.bud.lisp.lang.CompletedBudFuture;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lang.Symbol;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.parser.datum.BooleanDatum;
import me.predatorray.bud.lisp.parser.datum.CharacterDatum;
import me.predatorray.bud.lisp.parser.datum.CompoundDatum;
import me.predatorray.bud.lisp.parser.datum.Datum;
import me.predatorray.bud.lisp.parser.datum.DatumVisitor;
import me.predatorray.bud.lisp.parser.datum.NumberDatum;
import me.predatorray.bud.lisp.parser.datum.StringDatum;
import me.predatorray.bud.lisp.parser.datum.SymbolDatum;
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
