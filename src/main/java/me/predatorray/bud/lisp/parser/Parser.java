package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.parser.datum.Datum;
import me.predatorray.bud.lisp.parser.datum.DatumParserVisitor;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public List<Expression> parse(Iterable<? extends Token> tokens) {
        DatumParserVisitor visitor = new DatumParserVisitor();
        for (Token token : tokens) {
            token.accept(visitor);
        }
        List<Datum> dataOfProgram = visitor.getRootData();
        List<Expression> expressions = new ArrayList<>(dataOfProgram.size());
        for (Datum datum : dataOfProgram) {
            expressions.add(datum.getExpression());
        }
        return expressions;
    }
}
