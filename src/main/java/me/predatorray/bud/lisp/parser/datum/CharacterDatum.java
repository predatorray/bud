package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.CharacterToken;
import me.predatorray.bud.lisp.parser.CharacterLiteral;
import me.predatorray.bud.lisp.parser.Expression;

public class CharacterDatum extends SimpleDatum<CharacterToken> {

    public CharacterDatum(CharacterToken token) {
        super(token);
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }

    @Override
    public Expression getExpression() {
        return new CharacterLiteral(token);
    }

    public char getValue() {
        return token.getValue();
    }
}
