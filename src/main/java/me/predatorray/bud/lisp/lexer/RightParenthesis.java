package me.predatorray.bud.lisp.lexer;

public class RightParenthesis extends ConstantToken {

    private final TextLocation location;

    public RightParenthesis(TextLocation location) {
        super(location, ")");
        this.location = location;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RightParenthesis that = (RightParenthesis) o;

        return location.equals(that.location);

    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}
