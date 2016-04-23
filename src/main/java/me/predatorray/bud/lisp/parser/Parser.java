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
package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.parser.datum.Datum;
import me.predatorray.bud.lisp.parser.datum.DatumParserVisitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parser {

    private final DatumParserVisitor visitor;

    public Parser() {
        this(null);
    }

    public Parser(final ParserCallback parserCallback) {
        if (parserCallback == null) {
            visitor = new DatumParserVisitor();
        } else {
            visitor = new DatumParserVisitor() {
                @Override
                protected void rootDatumReady(Datum datumAtStackBottom) {
                    Expression expression = datumAtStackBottom.getExpression();
                    parserCallback.expressionReady(expression);
                }
            };
        }
    }

    public List<Expression> parse(Iterable<? extends Token> tokens) {
        for (Token token : tokens) {
            token.accept(visitor);
        }
        List<Datum> dataOfProgram = visitor.getRootData();
        List<Expression> expressions = new ArrayList<>(dataOfProgram.size());
        for (Datum datum : dataOfProgram) {
            expressions.add(datum.getExpression());
        }
        return expressions;
    }

    public void feed(Iterator<? extends Token> tokenIterator) {
        while (tokenIterator.hasNext()) {
            tokenIterator.next().accept(visitor);
        }
    }

    public interface ParserCallback {

        void expressionReady(Expression expression);
    }
}
