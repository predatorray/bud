package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.buitin.AbstractFunctionTest;
import me.predatorray.bud.lisp.evaluator.ArgumentTypeMismatchException;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudString;
import org.junit.Test;

import java.math.BigDecimal;

public class AddFunctionTest extends AbstractFunctionTest {

    public AddFunctionTest() {
        super(new AddFunction());
    }

    @Test
    public void testAdd1() {
        // (+) ==> 0
        assertApplyCorrectly("(+) ==> 0", new BudNumber(BigDecimal.ZERO));
    }

    @Test
    public void testAdd2() {
        // (+ -1) ==> -1
        assertApplyCorrectly("(+ -1) ==> -1", new BudNumber(new BigDecimal(-1)), new BudNumber(new BigDecimal(-1)));
    }

    @Test
    public void testAdd3() {
        // (+ 1 2) ==> 3
        assertApplyCorrectly("(+ 1 2) ==> 3", new BudNumber(new BigDecimal(3)),
                new BudNumber(BigDecimal.ONE), new BudNumber(new BigDecimal(2)));
    }

    @Test
    public void testAdd4() {
        // (+ -1 2 0 -1) ==> 0
        assertApplyCorrectly("(+ -1 2 0 -1) ==> 0", new BudNumber(BigDecimal.ZERO),
                new BudNumber(new BigDecimal(-1)),
                new BudNumber(new BigDecimal(2)),
                new BudNumber(BigDecimal.ZERO),
                new BudNumber(new BigDecimal(-1)));
    }

    @Test(expected = ArgumentTypeMismatchException.class)
    public void testAdd5() {
        // (+ "1" 1) ==> error
        exercise(new BudString("1"), new BudNumber(BigDecimal.ONE));
    }
}
