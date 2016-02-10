package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.IdentifierToken;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.Keyword;
import me.predatorray.bud.lisp.parser.Variable;

public class SymbolDatum implements Datum {

    private final IdentifierToken identifierToken;

    public SymbolDatum(IdentifierToken identifierToken) {
        this.identifierToken = identifierToken;
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }

    @Override
    public Expression getExpression() {
        Keyword keyword = Keyword.getKeywordIfApplicable(identifierToken);
        if (keyword == null) {
            return new Variable(identifierToken);
        } else {
            return keyword;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SymbolDatum that = (SymbolDatum) o;

        return identifierToken.equals(that.identifierToken);
    }

    @Override
    public int hashCode() {
        return identifierToken.hashCode();
    }

    @Override
    public String toString() {
        return identifierToken.toString();
    }

    public String getName() {
        return identifierToken.getName();
    }
}
