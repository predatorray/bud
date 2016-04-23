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

public class ExpressionVisitorAdapter implements ExpressionVisitor {

    @Override
    public void visit(BooleanLiteral booleanLiteral) {
        visitByDefault(booleanLiteral);
    }

    @Override
    public void visit(NumberLiteral numberLiteral) {
        visitByDefault(numberLiteral);
    }

    @Override
    public void visit(StringLiteral stringLiteral) {
        visitByDefault(stringLiteral);
    }

    @Override
    public void visit(CharacterLiteral characterLiteral) {
        visitByDefault(characterLiteral);
    }

    @Override
    public void visit(Variable variable) {
        visitByDefault(variable);
    }

    @Override
    public void visit(Keyword keyword) {
        visitByDefault(keyword);
    }

    @Override
    public void visit(ProcedureCall procedureCall) {
        visitByDefault(procedureCall);
    }

    @Override
    public void visit(QuoteSpecialForm quoteSpecialForm) {
        visitByDefault(quoteSpecialForm);
    }

    @Override
    public void visit(IfSpecialForm ifSpecialForm) {
        visitByDefault(ifSpecialForm);
    }

    @Override
    public void visit(AndSpecialForm andSpecialForm) {
        visitByDefault(andSpecialForm);
    }

    @Override
    public void visit(OrSpecialForm orSpecialForm) {
        visitByDefault(orSpecialForm);
    }

    @Override
    public void visit(ConditionSpecialForm conditionSpecialForm) {
        visitByDefault(conditionSpecialForm);
    }

    @Override
    public void visit(LambdaExpression lambdaExpression) {
        visitByDefault(lambdaExpression);
    }

    @Override
    public void visit(Expression other) {
        visitByDefault(other);
    }

    protected void visitByDefault(Expression expression) {
    }
}
