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
}
