package me.predatorray.bud.lisp.builtin.arithmetic;

import me.predatorray.bud.lisp.builtin.AbstractFunctionTest;
import me.predatorray.bud.lisp.evaluator.ArgumentTypeMismatchException;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudString;
import org.junit.Test;

import java.math.BigDecimal;

public class AbsFunctionTest extends AbstractFunctionTest {

    public AbsFunctionTest() {
        super(new AbsFunction());
    }

    @Test
    public void testAbs1() {
        assertApplyCorrectly("(abs 0) ==> 0", new BudNumber(BigDecimal.ZERO), new BudNumber(BigDecimal.ZERO));
    }

    @Test
    public void testAbs2() {
        assertApplyCorrectly("(abs 1) ==> 1", new BudNumber(BigDecimal.ONE), new BudNumber(BigDecimal.ONE));
    }

    @Test
    public void testAbs3() {
        assertApplyCorrectly("(abs (- 1)) ==> 1", new BudNumber(BigDecimal.ONE),
                new BudNumber(BigDecimal.ONE.negate()));
    }

    @Test
    public void testAbs4() {
        assertApplyCorrectly("(abs (- 1.2345)) ==> 1.2345", new BudNumber(new BigDecimal("1.2345")),
                new BudNumber(new BigDecimal("-1.2345")));
    }

    @Test(expected = ArgumentTypeMismatchException.class)
    public void testAbs5() {
        exercise(new BudString("1"));
    }
}
