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

import me.predatorray.bud.lisp.lexer.SingleQuoteToken;
import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.lexer.TokenVisitorAdapter;

import java.util.HashMap;
import java.util.Map;

class SingleQuoteRecorder extends TokenVisitorAdapter {

    private Token former;
    private final Map<Token, SingleQuoteToken> followingSingleQuote = new HashMap<>();

    @Override
    protected void visitDefault(Token token) {
        if (former != null && former instanceof SingleQuoteToken) {
            followingSingleQuote.put(token, ((SingleQuoteToken) former));
        }
        former = token;
    }

    public SingleQuoteToken getAndConsumeTheSingleQuotePrefix(Token token) {
        SingleQuoteToken singleQuoteToken = followingSingleQuote.get(token);
        if (singleQuoteToken == null) {
            return null;
        }
        followingSingleQuote.remove(token);
        return singleQuoteToken;
    }
}
