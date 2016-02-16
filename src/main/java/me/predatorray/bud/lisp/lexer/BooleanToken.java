package me.predatorray.bud.lisp.lexer;

public class BooleanToken implements Token {

    private final boolean value;
    private final TextLocation location;

    public BooleanToken(boolean value, TextLocation location) {
        this.value = value;
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

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooleanToken that = (BooleanToken) o;

        if (value != that.value) return false;
        return location != null ? location.equals(that.location) : that.location == null;
    }

    @Override
    public int hashCode() {
        int result = (value ? 1 : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return value ? "#t" : "#f";
    }
}
