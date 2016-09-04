package me.predatorray.bud.lisp.builtin.arithmetic;

import me.predatorray.bud.lisp.builtin.AbstractFunctionTest;
import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudString;
import org.junit.Test;

import java.math.BigDecimal;

public class IntegerPredicateTest extends AbstractFunctionTest {

    public IntegerPredicateTest() {
        super(new IntegerPredicate());
    }

    @Test
    public void testZeroIsInteger() {
        assertApplyCorrectly("(integer? 0) ==> #t", BudBoolean.TRUE, new BudNumber(BigDecimal.ZERO));
    }

    @Test
    public void testOneIsInteger() {
        assertApplyCorrectly("(integer? 1) ==> #t", BudBoolean.TRUE, new BudNumber(BigDecimal.ONE));
    }

    @Test
    public void testNegativeOneIsInteger() {
        assertApplyCorrectly("(integer? (- 1)) ==> #t", BudBoolean.TRUE, new BudNumber(BigDecimal.ONE.negate()));
    }

    @Test
    public void testAStringIsNotInteger() {
        assertApplyCorrectly("(integer? \"str\") ==> #f", BudBoolean.FALSE, new BudString("str"));
    }

    @Test
    public void testZeroPointOneIsNotInteger() {
        assertApplyCorrectly("(integer? 0.1) ==> #f", BudBoolean.FALSE, new BudNumber(new BigDecimal("0.1")));
    }

    @Test
    public void testOnePointZeroIsNotInteger() {
        assertApplyCorrectly("(integer? 1.0) ==> #f", BudBoolean.FALSE, new BudNumber(new BigDecimal("1.0")));
    }
}
