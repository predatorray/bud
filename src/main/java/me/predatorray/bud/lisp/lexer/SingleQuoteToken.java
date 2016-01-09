package me.predatorray.bud.lisp.lexer;

public class SingleQuoteToken extends ConstantToken {

    public SingleQuoteToken(TokenLocation location) {
        super(location, "'");
    }

    public void accept(LispTokenVisitor visitor) {
        visitor.visit(this);
    }
}
