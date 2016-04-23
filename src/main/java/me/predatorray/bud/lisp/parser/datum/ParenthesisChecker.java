/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Wenhao Ji
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.lexer.RightParenthesis;
import me.predatorray.bud.lisp.lexer.TokenVisitorAdapter;
import me.predatorray.bud.lisp.parser.ParserException;

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
        if (leftParenthesisStack.isEmpty()) {
            throw new ParserException("parentheses are not balanced");
        }
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
