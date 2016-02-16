package me.predatorray.bud.lisp.lexer;

public class RightParenthesis extends ConstantToken {

    public RightParenthesis(TextLocation location) {
        super(location, ")");
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
