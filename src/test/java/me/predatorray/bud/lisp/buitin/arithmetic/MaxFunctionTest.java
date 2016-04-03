package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.buitin.AbstractFunctionTest;
import me.predatorray.bud.lisp.evaluator.ArgumentTypeMismatchException;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudString;
import org.junit.Test;

import java.math.BigDecimal;

public class MaxFunctionTest extends AbstractFunctionTest {

    public MaxFunctionTest() {
        super(new MaxFunction());
    }

    @Test
    public void testMax1() {
        // (max 1 2 3) ==> 3
        assertApplyCorrectly("(max 1 2 3) ==> 3", new BudNumber(new BigDecimal(3)),
                new BudNumber(BigDecimal.ONE), new BudNumber(new BigDecimal(2)), new BudNumber(new BigDecimal(3)));
    }

    @Test
    public void testMax2() {
        // (max (- 1) (- 2) (- 3)) ==> -1
        assertApplyCorrectly("(max (- 1) (- 2) (- 3)) ==> -1", new BudNumber(new BigDecimal(-1)),
                new BudNumber(new BigDecimal(-1)), new BudNumber(new BigDecimal(-2)),
                new BudNumber(new BigDecimal(-3)));
    }

    @Test
    public void testMax3() {
        // (max 1) ==> 1
        assertApplyCorrectly("(max 1) ==> 1", new BudNumber(BigDecimal.ONE), new BudNumber(BigDecimal.ONE));
    }

    @Test
    public void testMax4() {
        // (max 0 0) ==> 0
        assertApplyCorrectly("(max 0 0) ==> 0", new BudNumber(BigDecimal.ZERO),
                new BudNumber(BigDecimal.ZERO), new BudNumber(BigDecimal.ZERO));
    }

    @Test(expected = ArgumentTypeMismatchException.class)
    public void testMax5() {
        // (max "str") ==> error
        exercise(new BudString("str"));
    }
}
