package me.predatorray.bud.lisp.builtin.arithmetic;

import me.predatorray.bud.lisp.builtin.AbstractFunctionTest;
import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudNumber;
import org.junit.Test;

import java.math.BigDecimal;

public class EvenPredicateTest extends AbstractFunctionTest {

    public EvenPredicateTest() {
        super(new EvenPredicate());
    }

    @Test
    public void testZeroIsEven() {
        assertApplyCorrectly("(even? 0) ==> #t", BudBoolean.TRUE, new BudNumber(BigDecimal.ZERO));
    }

    @Test
    public void testTwoIsEven() {
        assertApplyCorrectly("(even? 2) ==> #t", BudBoolean.TRUE, new BudNumber(new BigDecimal(2)));
    }

    @Test
    public void testOneIsNotEven() {
        assertApplyCorrectly("(even? 1) ==> #f", BudBoolean.FALSE, new BudNumber(new BigDecimal(1)));
    }

    @Test
    public void testZeroDotZeroIsNotEven() {
        assertApplyCorrectly("(even? 0.0) ==> #f", BudBoolean.FALSE, new BudNumber(new BigDecimal("0.0")));
    }

    @Test
    public void testNegateOneIsNotEven() {
        assertApplyCorrectly("(even? (- 1)) ==> #f", BudBoolean.FALSE, new BudNumber(new BigDecimal(-1)));
    }

    @Test
    public void testNegateTwoIsEven() {
        assertApplyCorrectly("(even? (- 2)) ==> #t", BudBoolean.TRUE, new BudNumber(new BigDecimal(-2)));
    }
}
