package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.evaluator.ArgumentTypeMismatchException;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class SubtractFunctionTest {

    private final SubtractFunction sut = new SubtractFunction();

    @Test
    public void testSubtract1() {
        // (-) ==> 0
        BudObject result = sut.apply(Collections.<BudObject>emptyList());
        assertEquals("(-) ==> 0", new BudNumber(BigDecimal.ZERO), result);
    }

    @Test
    public void testSubtract2() {
        // (- 1) ==> -1
        BudObject result = sut.apply(Collections.<BudObject>singletonList(new BudNumber(BigDecimal.ONE)));
        assertEquals("(- 1) ==> -1", new BudNumber(new BigDecimal(-1)), result);
    }

    @Test
    public void testSubtract3() {
        // (- 1 1) ==> 0
        BudObject result = sut.apply(Arrays.<BudObject>asList(
                new BudNumber(BigDecimal.ONE), new BudNumber(BigDecimal.ONE)));
        assertEquals("(- 1 1) ==> 0", new BudNumber(BigDecimal.ZERO), result);
    }

    @Test
    public void testSubtract4() {
        // (- 3 1 2) ==> 0
        BudObject result = sut.apply(Arrays.<BudObject>asList(
                new BudNumber(new BigDecimal(3)),
                new BudNumber(BigDecimal.ONE),
                new BudNumber(new BigDecimal(2))));
        assertEquals("(- 3 1 2) ==> 0", new BudNumber(BigDecimal.ZERO), result);
    }

    @Test
    public void testInspect1() {
        BudType returnType = sut.inspect(Arrays.asList(BudType.NUMBER, BudType.NUMBER));
        assertEquals(BudType.NUMBER, returnType);
    }

    @Test
    public void testInspect2() {
        BudType returnType = sut.inspect(Collections.<BudType>emptyList());
        assertEquals(BudType.NUMBER, returnType);
    }

    @Test(expected = ArgumentTypeMismatchException.class)
    public void testInspect3() {
        sut.inspect(Arrays.asList(BudType.STRING, BudType.NUMBER));
    }
}
