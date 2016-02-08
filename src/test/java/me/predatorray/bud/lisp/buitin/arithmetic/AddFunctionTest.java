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

public class AddFunctionTest {

    private final AddFunction sut = new AddFunction();

    @Test
    public void testAdd1() {
        // (+) ==> 0
        BudObject result = sut.apply(Collections.<BudObject>emptyList());
        assertEquals("(+) ==> 0", new BudNumber(BigDecimal.ZERO), result);
    }

    @Test
    public void testAdd2() {
        // (+ -1) ==> -1
        BudObject result = sut.apply(Collections.<BudObject>singletonList(
                new BudNumber(new BigDecimal(-1))));
        assertEquals("(+ -1) ==> -1", new BudNumber(new BigDecimal(-1)), result);
    }

    @Test
    public void testAdd3() {
        // (+ 1 2) ==> 3
        BudObject result = sut.apply(Arrays.<BudObject>asList(
                new BudNumber(BigDecimal.ONE), new BudNumber(new BigDecimal(2))));
        assertEquals("(+ 1 2) ==> 3", new BudNumber(new BigDecimal(3)), result);
    }

    @Test
    public void testAdd4() {
        // (+ -1 2 0 -1) ==> 0
        BudObject result = sut.apply(Arrays.<BudObject>asList(
                new BudNumber(new BigDecimal(-1)),
                new BudNumber(new BigDecimal(2)),
                new BudNumber(BigDecimal.ZERO),
                new BudNumber(new BigDecimal(-1))));
        assertEquals("(+ -1 2 0 -1) ==> 0", new BudNumber(BigDecimal.ZERO), result);
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
