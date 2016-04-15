package me.predatorray.bud.lisp.builtin.arithmetic;

import me.predatorray.bud.lisp.builtin.AbstractFunctionTest;
import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudString;
import org.junit.Test;

import java.math.BigDecimal;

public class ZeroPredicateTest extends AbstractFunctionTest {

    public ZeroPredicateTest() {
        super(new ZeroPredicate());
    }

    @Test
    public void testZeroIsZero() {
        assertApplyCorrectly("(zero? 0) ==> #t", BudBoolean.TRUE, new BudNumber(BigDecimal.ZERO));
    }

    @Test
    public void testZeroPointZeroIsZero() {
        assertApplyCorrectly("(zero? 0.0) ==> #t", BudBoolean.TRUE, new BudNumber(new BigDecimal("0.0")));
    }

    @Test
    public void testString0IsNotZero() {
        assertApplyCorrectly("(zero? \"0\") ==> #f", BudBoolean.FALSE, new BudString("0"));
    }
}
