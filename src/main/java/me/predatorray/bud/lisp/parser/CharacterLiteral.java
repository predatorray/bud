package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.BudCharacter;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lexer.CharacterToken;

public class CharacterLiteral extends TokenLocatedExpression {

    private final CharacterToken characterToken;
    private final char value;

    public CharacterLiteral(CharacterToken characterToken) {
        super(characterToken);
        this.characterToken = characterToken;
        this.value = characterToken.getValue();
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public BudObject evaluate(Environment environment, Evaluator evaluator) {
        return new BudCharacter(value);
    }

    public char getValue() {
        return value;
    }

    @Override
    public String toString() {
        return characterToken.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharacterLiteral that = (CharacterLiteral) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (int) value;
    }
}
