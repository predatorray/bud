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
