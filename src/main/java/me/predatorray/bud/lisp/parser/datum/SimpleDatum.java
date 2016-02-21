package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.util.Validation;

abstract class SimpleDatum<T extends Token> implements Datum {

    protected final T token;

    public SimpleDatum(T token) {
        this.token = Validation.notNull(token);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleDatum<?> that = (SimpleDatum<?>) o;

        return token.equals(that.token);
    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }

    @Override
    public String toString() {
        return token.toString();
    }
}
