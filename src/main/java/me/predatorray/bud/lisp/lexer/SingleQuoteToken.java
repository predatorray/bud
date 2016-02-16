package me.predatorray.bud.lisp.lexer;

public class SingleQuoteToken extends ConstantToken {

    public SingleQuoteToken(TextLocation location) {
        super(location, "'");
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
