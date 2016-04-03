package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.buitin.AbstractFunctionTest;
import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudString;
import org.junit.Test;

import java.math.BigDecimal;

public class NegativePredicateTest extends AbstractFunctionTest {

    public NegativePredicateTest() {
        super(new NegativePredicate());
    }

    @Test
    public void testOneIsNotNegative() {
        assertApplyCorrectly("(negative? 1) ==> #f", BudBoolean.FALSE, new BudNumber(BigDecimal.ONE));
    }

    @Test
    public void testZeroIsNotNegative() {
        assertApplyCorrectly("(negative? 0) ==> #f", BudBoolean.FALSE, new BudNumber(BigDecimal.ZERO));
    }

    @Test
    public void testNegateOneIsNegative() {
        assertApplyCorrectly("(negative? (- 1)) ==> #t", BudBoolean.TRUE, new BudNumber(new BigDecimal(-1)));
    }

    @Test
    public void testString1NotPositive() {
        assertApplyCorrectly("(negative? \"-1\") ==> #f", BudBoolean.FALSE, new BudString("-1"));
    }

}