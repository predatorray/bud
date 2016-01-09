package me.predatorray.bud.lisp.lexer;

public interface Token {

    TokenLocation getLocation();

    void accept(LispTokenVisitor visitor);

    @Override
    String toString();
}
