package me.predatorray.bud.lisp.builtin.arithmetic;

import me.predatorray.bud.lisp.builtin.AbstractFunctionTest;
import me.predatorray.bud.lisp.evaluator.ArgumentTypeMismatchException;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudString;
import org.junit.Test;

import java.math.BigDecimal;

public class MinFunctionTest extends AbstractFunctionTest {

    public MinFunctionTest() {
        super(new MinFunction());
    }

    @Test
    public void testMin1() {
        // (min 1 2 3) ==> 1
        assertApplyCorrectly("(min 1 2 3) ==> 1", new BudNumber(new BigDecimal(1)),
                new BudNumber(BigDecimal.ONE), new BudNumber(new BigDecimal(2)), new BudNumber(new BigDecimal(3)));
    }

    @Test
    public void testMin2() {
        // (max (- 1) (- 2) (- 3)) ==> -3
        assertApplyCorrectly("(min (- 1) (- 2) (- 3)) ==> -3", new BudNumber(new BigDecimal(-3)),
                new BudNumber(new BigDecimal(-1)), new BudNumber(new BigDecimal(-2)),
                new BudNumber(new BigDecimal(-3)));
    }

    @Test
    public void testMin3() {
        // (max 1) ==> 1
        assertApplyCorrectly("(min 1) ==> 1", new BudNumber(BigDecimal.ONE), new BudNumber(BigDecimal.ONE));
    }

    @Test
    public void testMin4() {
        // (max 0 0) ==> 0
        assertApplyCorrectly("(min 0 0) ==> 0", new BudNumber(BigDecimal.ZERO),
                new BudNumber(BigDecimal.ZERO), new BudNumber(BigDecimal.ZERO));
    }

    @Test(expected = ArgumentTypeMismatchException.class)
    public void testMin5() {
        // (max "str") ==> error
        exercise(new BudString("str"));
    }
}
