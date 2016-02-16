package me.predatorray.bud.lisp.lexer;

abstract class ConstantToken implements Token {

    private final TextLocation location;
    private final String textForm;

    protected ConstantToken(TextLocation location, String textForm) {
        this.location = location;
        this.textForm = textForm;
    }

    @Override
    public TextLocation getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return textForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantToken that = (ConstantToken) o;

        return location != null ? location.equals(that.location) : that.location == null;
    }

    @Override
    public int hashCode() {
        return location != null ? location.hashCode() : 0;
    }
}
