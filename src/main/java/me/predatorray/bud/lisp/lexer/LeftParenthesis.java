package me.predatorray.bud.lisp.lexer;

public class LeftParenthesis extends ConstantToken {

    private final TextLocation location;

    public LeftParenthesis(TextLocation location) {
        super(location, "(");
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

        LeftParenthesis that = (LeftParenthesis) o;

        return location.equals(that.location);

    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}
