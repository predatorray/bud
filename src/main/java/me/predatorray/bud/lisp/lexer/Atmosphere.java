package me.predatorray.bud.lisp.lexer;

public class Atmosphere extends ConstantToken {

    public Atmosphere(TextLocation location) {
        super(location, " ");
    }

    @Override
    public void accept(TokenVisitor ignored) {
    }
}
