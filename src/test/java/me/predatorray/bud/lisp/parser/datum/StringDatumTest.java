package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.StringToken;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.StringLiteral;
import me.predatorray.bud.lisp.test.AbstractBudLispTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringDatumTest extends AbstractBudLispTest {

    @Test
    public void testGetExpressionOfString() {
        StringDatum datum = new StringDatum(new StringToken("foobar", DUMMY_LOCATION));
        Expression expression = datum.getExpression();
        assertTrue(expression instanceof StringLiteral);
        StringLiteral stringLiteral = (StringLiteral) expression;
        assertEquals("foobar", stringLiteral.getValue());
        assertEquals(DUMMY_LOCATION, stringLiteral.getLocation());
    }
}