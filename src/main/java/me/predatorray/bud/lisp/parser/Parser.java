package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.parser.datum.Datum;
import me.predatorray.bud.lisp.parser.datum.DatumParserVisitor;

public class Parser {

    public Expression parse(Iterable<? extends Token> tokens) {
        DatumParserVisitor visitor = new DatumParserVisitor();
        for (Token token : tokens) {
            token.accept(visitor);
        }
        Datum rootDatum = visitor.getRootDatum();
        return rootDatum.getExpression();
    }
}
