package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.BooleanToken;
import me.predatorray.bud.lisp.lexer.IdentifierToken;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.lexer.NumberToken;
import me.predatorray.bud.lisp.lexer.RightParenthesis;
import me.predatorray.bud.lisp.lexer.SingleQuoteToken;
import me.predatorray.bud.lisp.lexer.StringToken;
import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.lexer.TokenVisitor;
import me.predatorray.bud.lisp.parser.ParserException;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class DatumParserVisitor implements TokenVisitor {

    private final ParenthesisChecker parenthesisChecker = new ParenthesisChecker();
    private final Stack<List<Datum>> dataStack;

    public DatumParserVisitor() {
        dataStack = new Stack<List<Datum>>();
        dataStack.push(new LinkedList<Datum>());
    }

    private void appendOnTopOfStack(Datum datum) {
        List<Datum> top = dataStack.peek();
        if (top == null) {
            throw new ParserException(); // TODO
        }
        top.add(datum);
    }

    @Override
    public void visit(LeftParenthesis leftParenthesis) {
        parenthesisChecker.visit(leftParenthesis);
        dataStack.push(new LinkedList<Datum>());
    }

    @Override
    public void visit(RightParenthesis rightParenthesis) {
        parenthesisChecker.visit(rightParenthesis);
        LeftParenthesis leftParenthesis = parenthesisChecker.getLeftParenthesis(rightParenthesis);

        List<Datum> data = dataStack.pop();
        CompoundDatum compoundDatum = new CompoundDatum(data, leftParenthesis);

        appendOnTopOfStack(compoundDatum);
    }

    @Override
    public void visit(SingleQuoteToken singleQuoteToken) {
        // TODO
    }

    @Override
    public void visit(StringToken stringToken) {
        appendOnTopOfStack(new StringDatum(stringToken));
    }

    @Override
    public void visit(BooleanToken booleanToken) {
        appendOnTopOfStack(new BooleanDatum(booleanToken));
    }

    @Override
    public void visit(NumberToken numberToken) {
        appendOnTopOfStack(new NumberDatum(numberToken));
    }

    @Override
    public void visit(IdentifierToken identifierToken) {
        appendOnTopOfStack(new SymbolDatum(identifierToken));
    }

    @Override
    public void visit(Token unknown) {
        throw new ParserException("unknown token: " + unknown);
    }

    public List<Datum> getRootData() {
        if (!parenthesisChecker.isBalanced()) {
            throw new ParserException("parentheses are not balanced");
        }
        return dataStack.peek();
    }
}
