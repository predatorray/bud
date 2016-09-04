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

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.Continuous;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lexer.IdentifierToken;
import me.predatorray.bud.lisp.util.Sets;
import me.predatorray.bud.lisp.util.Validation;

import java.util.Set;

public class Keyword extends TokenLocatedExpression {

    public static final Set<String> EXPRESSION_KEYWORDS = Sets.asSet(
            "quote", "lambda", "if", "set!", "begin", "cond", "and", "or", "case",
            "let", "let*", "letrec", "do", "delay", "quasiquote");

    public static final Set<String> SYNTACTIC_KEYWORDS = Sets.union(Sets.asSet(
            "else", "=>", "define", "unquote", "unquote-splicing"), EXPRESSION_KEYWORDS);

    public static Keyword getKeywordIfApplicable(IdentifierToken identifierToken) {
        Validation.notNull(identifierToken);
        if (SYNTACTIC_KEYWORDS.contains(identifierToken.getName())) {
            return new Keyword(identifierToken);
        } else {
            return null;
        }
    }

    private final String keywordName;

    public Keyword(IdentifierToken identifierToken) {
        super(identifierToken);
        this.keywordName = identifierToken.getName();
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public Continuous evaluate(Environment environment, Evaluator evaluator) {
        throw new EvaluatingException("cannot evaluate a keyword", this);
    }

    public String getKeywordName() {
        return keywordName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Keyword keyword = (Keyword) o;

        return keywordName.equals(keyword.keywordName);
    }

    public boolean isExpressionKeyword() {
        return EXPRESSION_KEYWORDS.contains(keywordName);
    }

    @Override
    public int hashCode() {
        return keywordName.hashCode();
    }

    @Override
    public String toString() {
        return keywordName;
    }
}
