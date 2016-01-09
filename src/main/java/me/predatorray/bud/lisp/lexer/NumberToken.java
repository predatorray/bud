package me.predatorray.bud.lisp.lexer;

public class NumberToken implements Token {

    private final TextLocation location;

    public NumberToken(TextLocation location) {
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
        return null; // TODO
    }
}
