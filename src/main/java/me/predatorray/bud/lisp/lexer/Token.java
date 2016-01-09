package me.predatorray.bud.lisp.lexer;

public interface Token {

    TextLocation getLocation();

    void accept(TokenVisitor visitor);

    @Override
    String toString();
}
