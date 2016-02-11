package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.IdentifierToken;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.Keyword;
import me.predatorray.bud.lisp.parser.Variable;
import me.predatorray.bud.lisp.test.AbstractBudLispTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SymbolDatumTest extends AbstractBudLispTest {

    @Test
    public void testGetExpressionOfKeyword() {
        SymbolDatum datum = new SymbolDatum(new IdentifierToken("lambda", DUMMY_LOCATION));
        Expression expression = datum.getExpression();
        assertTrue(expression instanceof Keyword);
        Keyword keyword = (Keyword) expression;
        assertEquals("lambda", keyword.getKeywordName());
        assertEquals(DUMMY_LOCATION, keyword.getLocation());
    }

    @Test
    public void testGetExpressionOfVariable() {
        SymbolDatum datum = new SymbolDatum(new IdentifierToken("foobar", DUMMY_LOCATION));
        Expression expression = datum.getExpression();
        assertTrue(expression instanceof Variable);
        Variable variable = (Variable) expression;
        assertEquals("foobar", variable.getVariableName());
        assertEquals(DUMMY_LOCATION, variable.getLocation());
    }
}