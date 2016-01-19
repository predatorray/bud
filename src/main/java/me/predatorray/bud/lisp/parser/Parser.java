package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.BooleanToken;
import me.predatorray.bud.lisp.lexer.IdentifierToken;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.lexer.NumberToken;
import me.predatorray.bud.lisp.lexer.RightParenthesis;
import me.predatorray.bud.lisp.lexer.SingleQuoteToken;
import me.predatorray.bud.lisp.lexer.StringToken;
import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.lexer.TokenVisitor;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Parser {

    public Datum parse(Iterable<? extends Token> tokens) {
        final ParserTokenVisitor visitor = new ParserTokenVisitor();
        for (Token token : tokens) {
            token.accept(visitor);
        }
        return visitor.getRootDatum();
    }
}

class ParserTokenVisitor implements TokenVisitor {

    private final ParenthesisChecker parenthesisChecker = new ParenthesisChecker();
    private final Stack<List<Datum>> dataStack;

    ParserTokenVisitor() {
        dataStack = new Stack<List<Datum>>();
        dataStack.push(new LinkedList<Datum>());
    }

    @Override
    public void visit(LeftParenthesis leftParenthesis) {
        parenthesisChecker.visit(leftParenthesis);
        dataStack.push(new LinkedList<Datum>());
    }

    @Override
    public void visit(RightParenthesis rightParenthesis) {
        parenthesisChecker.visit(rightParenthesis);

        List<Datum> data = dataStack.pop();
        ListDatum listDatum = new ListDatum(data, parenthesisChecker.getLeftParenthesis(rightParenthesis));
        List<Datum> top = dataStack.peek();
        top.add(listDatum);
    }

    @Override
    public void visit(SingleQuoteToken singleQuoteToken) {
        // TODO
    }

    @Override
    public void visit(StringToken stringToken) {
        List<Datum> top = dataStack.peek();
        top.add(new StringLiteral(stringToken));
    }

    @Override
    public void visit(BooleanToken booleanToken) {
        List<Datum> top = dataStack.peek();
        top.add(new BooleanLiteral(booleanToken));
    }

    @Override
    public void visit(NumberToken numberToken) {
        List<Datum> top = dataStack.peek();
        top.add(new NumberLiteral(numberToken));
    }

    @Override
    public void visit(IdentifierToken identifierToken) {
        List<Datum> top = dataStack.peek();
        Keyword keyword = Keyword.getKeywordIfApplicable(identifierToken);
        if (keyword == null) {
            top.add(new Variable(identifierToken));
        } else {
            top.add(keyword);
        }
    }

    @Override
    public void visit(Token ignored) {
    }

    public Datum getRootDatum() {
        if (!parenthesisChecker.isBalanced()) {
            throw new ParserException("parentheses are not balanced");
        }
        List<Datum> root = dataStack.peek();
        return root.get(0); // TODO
    }
}
