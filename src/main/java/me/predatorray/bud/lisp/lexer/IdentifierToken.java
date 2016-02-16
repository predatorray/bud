package me.predatorray.bud.lisp.lexer;

import me.predatorray.bud.lisp.util.Validation;

public class IdentifierToken implements Token {

    private final String name;
    private final TextLocation location;

    public IdentifierToken(String name, TextLocation location) {
        this.name = Validation.notNull(name, "the name of an identifier must not be null");
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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdentifierToken that = (IdentifierToken) o;

        if (!name.equals(that.name)) return false;
        return location != null ? location.equals(that.location) : that.location == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
