package me.predatorray.bud.lisp;

import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.test.AbstractInterpreterTest;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;

public class TailRecursionTest extends AbstractInterpreterTest {

    @Ignore("tail recursion is not implemented")
    @Test
    public void testTailRecursion1() throws Exception {
        assertInterpretCorrectly(new BudNumber(new BigDecimal(50005000)), "accumulate-1-10000.bud");
    }
}
