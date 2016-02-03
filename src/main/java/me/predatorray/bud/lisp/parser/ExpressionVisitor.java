package me.predatorray.bud.lisp.parser;

public interface ExpressionVisitor {

    void visit(BooleanLiteral booleanLiteral);

    void visit(NumberLiteral numberLiteral);

    void visit(StringLiteral stringLiteral);

    void visit(Variable variable);

    void visit(Keyword keyword);

    void visit(ProcedureCall procedureCall);

    void visit(QuoteSpecialForm quoteSpecialForm);

    void visit(LambdaExpression lambdaExpression);

    void visit(Definition definition);

    void visit(ListExpression listExpression);

    void visit(Expression other);
}
