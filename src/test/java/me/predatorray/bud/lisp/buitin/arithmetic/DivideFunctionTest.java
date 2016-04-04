package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.buitin.AbstractFunctionTest;
import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudNumber;
import org.junit.Test;

import java.math.BigDecimal;

public class DivideFunctionTest extends AbstractFunctionTest {

    public DivideFunctionTest() {
        super(new DivideFunction());
    }

    @Test
    public void testDivide1() {
        assertApplyCorrectly("(/) ==> 1", new BudNumber(BigDecimal.ONE));
    }

    @Test
    public void testDivide2() {
        assertApplyCorrectly("(/ 2) ==> 0.5", new BudNumber(new BigDecimal("0.5")), new BudNumber(new BigDecimal(2)));
    }

    @Test
    public void testDivide3() {
        assertApplyCorrectly("(/ 3 2) ==> 1.5", new BudNumber(new BigDecimal("1.5")),
                new BudNumber(new BigDecimal(3)), new BudNumber(new BigDecimal(2)));
    }

    @Test
    public void testDivide4() {
        assertApplyCorrectly("(/ 4 2 1 2) ==> 1", new BudNumber(BigDecimal.ONE),
                new BudNumber(new BigDecimal(4)), new BudNumber(new BigDecimal(2)),
                new BudNumber(new BigDecimal(1)), new BudNumber(new BigDecimal(2)));
    }

    @Test(expected = EvaluatingException.class)
    public void testDivide5() {
        exercise(new BudNumber(new BigDecimal(1)), new BudNumber(new BigDecimal(0)));
    }

    @Test
    public void testDivide6() {
        assertApplyCorrectly("(/ 1 3) ==> 0.3333333", new BudNumber(new BigDecimal("0.3333333")),
                new BudNumber(new BigDecimal(1)), new BudNumber(new BigDecimal(3)));
    }
}
