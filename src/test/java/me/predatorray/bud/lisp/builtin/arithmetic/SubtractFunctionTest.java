package me.predatorray.bud.lisp.builtin.arithmetic;

import me.predatorray.bud.lisp.builtin.AbstractFunctionTest;
import me.predatorray.bud.lisp.evaluator.ArgumentTypeMismatchException;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudString;
import org.junit.Test;

import java.math.BigDecimal;

public class SubtractFunctionTest extends AbstractFunctionTest {

    public SubtractFunctionTest() {
        super(new SubtractFunction());
    }

    @Test
    public void testSubtract1() {
        // (-) ==> 0
        assertApplyCorrectly("(-) ==> 0", new BudNumber(BigDecimal.ZERO));
    }

    @Test
    public void testSubtract2() {
        // (- 1) ==> -1
        assertApplyCorrectly("(- 1) ==> -1", new BudNumber(new BigDecimal(-1)), new BudNumber(BigDecimal.ONE));
    }

    @Test
    public void testSubtract3() {
        // (- 1 1) ==> 0
        assertApplyCorrectly("(- 1 1) ==> 0", new BudNumber(BigDecimal.ZERO),
                new BudNumber(BigDecimal.ONE), new BudNumber(BigDecimal.ONE));
    }

    @Test
    public void testSubtract4() {
        // (- 3 1 2) ==> 0
        assertApplyCorrectly("(- 3 1 2) ==> 0", new BudNumber(BigDecimal.ZERO),
                new BudNumber(new BigDecimal(3)),
                new BudNumber(BigDecimal.ONE),
                new BudNumber(new BigDecimal(2)));
    }

    @Test(expected = ArgumentTypeMismatchException.class)
    public void testSubtract5() {
        // (- "1") ==> error
        exercise(new BudString("1"), new BudNumber(BigDecimal.ONE));
    }
}
