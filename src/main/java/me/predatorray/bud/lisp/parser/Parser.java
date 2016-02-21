package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.parser.datum.Datum;
import me.predatorray.bud.lisp.parser.datum.DatumParserVisitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parser {

    private final DatumParserVisitor visitor;

    public Parser() {
        this(null);
    }

    public Parser(final ParserCallback parserCallback) {
        if (parserCallback == null) {
            visitor = new DatumParserVisitor();
        } else {
            visitor = new DatumParserVisitor() {
                @Override
                protected void rootDatumReady(Datum datumAtStackBottom) {
                    Expression expression = datumAtStackBottom.getExpression();
                    parserCallback.expressionReady(expression);
                }
            };
        }
    }

    public List<Expression> parse(Iterable<? extends Token> tokens) {
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

    public void feed(Iterator<? extends Token> tokenIterator) {
        while (tokenIterator.hasNext()) {
            tokenIterator.next().accept(visitor);
        }
    }

    public interface ParserCallback {

        void expressionReady(Expression expression);
    }
}
