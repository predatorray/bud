package me.predatorray.bud.lisp.parser;

public interface Datum {

    void accept(AstNodeVisitor astNodeVisitor);
}
