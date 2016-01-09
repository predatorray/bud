package me.predatorray.bud.lisp.lexer;

public class IdentifierToken implements Token {

    private final String name;
    private final TextLocation location;

    public IdentifierToken(String name, TextLocation location) {
        this.name = name;
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
}
