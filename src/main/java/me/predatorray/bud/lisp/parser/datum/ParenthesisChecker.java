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

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class ParenthesisChecker implements TokenVisitor {

    private final Stack<LeftParenthesis> leftParenthesisStack;
    private final Map<RightParenthesis, LeftParenthesis> parenthesisMap;

    public ParenthesisChecker() {
        leftParenthesisStack = new Stack<>();
        parenthesisMap = new HashMap<>();
    }

    @Override
    public void visit(LeftParenthesis leftParenthesis) {
        leftParenthesisStack.push(leftParenthesis);
    }

    @Override
    public void visit(RightParenthesis rightParenthesis) {
        LeftParenthesis leftParenthesis = leftParenthesisStack.pop();
        parenthesisMap.put(rightParenthesis, leftParenthesis);
    }

    @Override
    public void visit(SingleQuoteToken singleQuoteToken) {
    }

    @Override
    public void visit(StringToken stringToken) {
    }

    @Override
    public void visit(BooleanToken booleanToken) {
    }

    @Override
    public void visit(NumberToken numberToken) {
    }

    @Override
    public void visit(IdentifierToken identifierToken) {
    }

    @Override
    public void visit(Token other) {
    }

    public LeftParenthesis getLeftParenthesis(RightParenthesis rightParenthesis) {
        return parenthesisMap.get(rightParenthesis);
    }

    public boolean isBalanced() {
        return leftParenthesisStack.isEmpty();
    }
}
