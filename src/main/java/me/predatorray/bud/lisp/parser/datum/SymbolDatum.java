package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.IdentifierToken;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.Keyword;
import me.predatorray.bud.lisp.parser.Variable;

public class SymbolDatum extends SimpleDatum<IdentifierToken> {

    public SymbolDatum(IdentifierToken identifierToken) {
        super(identifierToken);
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }

    @Override
    public Expression getExpression() {
        Keyword keyword = Keyword.getKeywordIfApplicable(token);
        if (keyword == null) {
            return new Variable(token);
        } else {
            return keyword;
        }
    }

    public String getName() {
        return token.getName();
    }
}
