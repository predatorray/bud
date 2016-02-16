package me.predatorray.bud.lisp.lexer;

public class TokenVisitorAdapter implements TokenVisitor {

    @Override
    public void visit(LeftParenthesis leftParenthesis) {
        visitDefault(leftParenthesis);
    }

    @Override
    public void visit(RightParenthesis rightParenthesis) {
        visitDefault(rightParenthesis);
    }

    @Override
    public void visit(SingleQuoteToken singleQuoteToken) {
        visitDefault(singleQuoteToken);
    }

    @Override
    public void visit(StringToken stringToken) {
        visitDefault(stringToken);
    }

    @Override
    public void visit(BooleanToken booleanToken) {
        visitDefault(booleanToken);
    }

    @Override
    public void visit(NumberToken numberToken) {
        visitDefault(numberToken);
    }

    @Override
    public void visit(CharacterToken characterToken) {
        visitDefault(characterToken);
    }

    @Override
    public void visit(IdentifierToken identifierToken) {
        visitDefault(identifierToken);
    }

    @Override
    public void visit(Token other) {
        visitDefault(other);
    }

    protected void visitDefault(Token token) {
    }
}
