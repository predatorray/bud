package me.predatorray.bud.lisp.lexer;

public interface TokenVisitor {

    void visit(LeftParenthesis leftParenthesis);

    void visit(RightParenthesis rightParenthesis);

    void visit(SingleQuoteToken singleQuoteToken);

    void visit(StringToken stringToken);

    void visit(Token other);
}
