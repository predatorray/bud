package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.buitin.AbstractFunctionTest;
import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudNumber;
import org.junit.Test;

import java.math.BigDecimal;

public class OddPredicateTest extends AbstractFunctionTest {

    public OddPredicateTest() {
        super(new OddPredicate());
    }

    @Test
    public void testZeroIsNotOdd() {
        assertApplyCorrectly("(odd? 0) ==> #f", BudBoolean.FALSE, new BudNumber(BigDecimal.ZERO));
    }

    @Test
    public void testOneIsOdd() {
        assertApplyCorrectly("(odd? 1) ==> #t", BudBoolean.TRUE, new BudNumber(new BigDecimal(1)));
    }

    @Test
    public void testTwoIsNotOdd() {
        assertApplyCorrectly("(odd? 2) ==> #f", BudBoolean.FALSE, new BudNumber(new BigDecimal(2)));
    }

    @Test
    public void testOneDotZeroIsNotOdd() {
        assertApplyCorrectly("(odd? 1.0) ==> #f", BudBoolean.FALSE, new BudNumber(new BigDecimal("1.0")));
    }

    @Test
    public void testNegateOneIsOdd() {
        assertApplyCorrectly("(odd? (- 1)) ==> #t", BudBoolean.TRUE, new BudNumber(new BigDecimal(-1)));
    }

    @Test
    public void testNegateTwoIsNotOdd() {
        assertApplyCorrectly("(odd? (- 2)) ==> #f", BudBoolean.FALSE, new BudNumber(new BigDecimal(-2)));
    }
}
