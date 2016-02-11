package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.BooleanToken;
import me.predatorray.bud.lisp.parser.BooleanLiteral;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.test.AbstractBudLispTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BooleanDatumTest extends AbstractBudLispTest {

    @Test
    public void testGetExpressionOfTrue() {
        BooleanDatum datum = new BooleanDatum(new BooleanToken(true, DUMMY_LOCATION));
        Expression expression = datum.getExpression();
        assertTrue(expression instanceof BooleanLiteral);
        BooleanLiteral booleanLiteral = (BooleanLiteral) expression;
        assertTrue(booleanLiteral.getValue());
        assertEquals(DUMMY_LOCATION, booleanLiteral.getLocation());
    }

    @Test
    public void testGetExpressionOfFalse() {
        BooleanDatum datum = new BooleanDatum(new BooleanToken(false, DUMMY_LOCATION));
        Expression expression = datum.getExpression();
        assertTrue(expression instanceof BooleanLiteral);
        BooleanLiteral booleanLiteral = (BooleanLiteral) expression;
        assertFalse(booleanLiteral.getValue());
        assertEquals(DUMMY_LOCATION, booleanLiteral.getLocation());
    }
}