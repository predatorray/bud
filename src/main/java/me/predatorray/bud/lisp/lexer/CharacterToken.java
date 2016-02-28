package me.predatorray.bud.lisp.lexer;

public class CharacterToken implements Token {

    private final char c;
    private final TextLocation location;

    public CharacterToken(char c, TextLocation location) {
        this.c = c;
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

    public char getValue() {
        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharacterToken that = (CharacterToken) o;

        if (c != that.c) return false;
        return location != null ? location.equals(that.location) : that.location == null;
    }

    @Override
    public int hashCode() {
        int result = (int) c;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("#\\%03o", (int) c);
    }
}
