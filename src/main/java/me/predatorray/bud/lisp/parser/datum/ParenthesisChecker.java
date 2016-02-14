package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.lexer.RightParenthesis;
import me.predatorray.bud.lisp.lexer.TokenVisitorAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class ParenthesisChecker extends TokenVisitorAdapter {

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

    public LeftParenthesis getLeftParenthesis(RightParenthesis rightParenthesis) {
        return parenthesisMap.get(rightParenthesis);
    }

    public boolean isBalanced() {
        return leftParenthesisStack.isEmpty();
    }
}
