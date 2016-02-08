package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.IdentifierToken;
import me.predatorray.bud.lisp.util.Validation;

public class Variable extends TokenLocatedExpression {

    private final String variableName;

    public Variable(IdentifierToken identifierToken) {
        super(Validation.notNull(identifierToken, "identifierToken must not be null"));
        variableName = identifierToken.getName();
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    public String getVariableName() {
        return variableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        return variableName.equals(variable.variableName);

    }

    @Override
    public String toString() {
        return variableName;
    }

    @Override
    public int hashCode() {
        return variableName.hashCode();
    }
}
