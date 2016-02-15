package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.NumberToken;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.NumberLiteral;
import me.predatorray.bud.lisp.test.AbstractBudLispTest;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NumberDatumTest extends AbstractBudLispTest {

    @Test
    public void testGetExpressionOfNumber() {
        NumberDatum datum = new NumberDatum(new NumberToken(BigDecimal.ONE, DUMMY_LOCATION));
        Expression expression = datum.getExpression();
        assertTrue(expression instanceof NumberLiteral);
        NumberLiteral numberLiteral = (NumberLiteral) expression;
        assertEquals(BigDecimal.ONE, numberLiteral.getValue());
        assertEquals(DUMMY_LOCATION, numberLiteral.getLocation());
    }
}