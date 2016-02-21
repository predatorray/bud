package me.predatorray.bud.lisp.lexer;

public class LeftParenthesis extends ConstantToken {

    public LeftParenthesis(TextLocation location) {
        super(location, "(");
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
