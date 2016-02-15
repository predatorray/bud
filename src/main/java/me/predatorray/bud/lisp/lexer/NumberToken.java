package me.predatorray.bud.lisp.lexer;

import me.predatorray.bud.lisp.util.Validation;

import java.math.BigDecimal;

public class NumberToken implements Token {

    private final BigDecimal decimal;
    private final TextLocation location;

    public NumberToken(BigDecimal decimal, TextLocation location) {
        this.decimal = Validation.notNull(decimal);
        this.location = location;
    }

    @Override
    public TextLocation getLocation() {
        return location;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return decimal.toString();
    }

    public BigDecimal getValue() {
        return decimal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberToken that = (NumberToken) o;

        if (!decimal.equals(that.decimal)) return false;
        return location != null ? location.equals(that.location) : that.location == null;
    }

    @Override
    public int hashCode() {
        int result = decimal.hashCode();
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
