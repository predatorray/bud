package me.predatorray.bud.lisp.lexer;

public class Atmosphere extends ConstantToken {

    public Atmosphere(TokenLocation location) {
        super(location, " ");
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
