package me.predatorray.bud.lisp.lexer;

public class RightParenthesis implements LispToken {

    private static final String LEFT_PARENTHESIS = "(";

    private final TokenLocation location;

    public RightParenthesis(TokenLocation location) {
        this.location = location;
    }

    public TokenLocation getLocation() {
        return location;
    }

    public void accept(LispTokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return LEFT_PARENTHESIS;
    }
}
