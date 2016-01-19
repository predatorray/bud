package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.Validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListDatum extends TokenLocatedDatum {

    private final List<Datum> data;

    public ListDatum(List<? extends Datum> data, LeftParenthesis leading) {
        super(Validation.notNull(leading));
        this.data = Collections.unmodifiableList(new ArrayList<Datum>(Validation.notNull(data)));
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }

    public List<Datum> getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListDatum listDatum = (ListDatum) o;

        return data.equals(listDatum.data);

    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }
}
