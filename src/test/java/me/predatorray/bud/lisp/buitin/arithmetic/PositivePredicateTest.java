package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.buitin.AbstractFunctionTest;
import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudString;
import org.junit.Test;

import java.math.BigDecimal;

public class PositivePredicateTest extends AbstractFunctionTest {

    public PositivePredicateTest() {
        super(new PositivePredicate());
    }

    @Test
    public void testOneIsPositive() {
        assertApplyCorrectly("(positive? 1) ==> #t", BudBoolean.TRUE, new BudNumber(BigDecimal.ONE));
    }

    @Test
    public void testZeroIsNotPositive() {
        assertApplyCorrectly("(positive? 0) ==> #f", BudBoolean.FALSE, new BudNumber(BigDecimal.ZERO));
    }

    @Test
    public void testNegateOneIsNotPositive() {
        assertApplyCorrectly("(positive? (- 1)) ==> #f", BudBoolean.FALSE, new BudNumber(new BigDecimal(-1)));
    }

    @Test
    public void testString1NotPositive() {
        assertApplyCorrectly("(positive? \"1\") ==> #f", BudBoolean.FALSE, new BudString("1"));
    }
}
