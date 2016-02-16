package me.predatorray.bud.lisp;

import me.predatorray.bud.lisp.lang.BudCharacter;
import me.predatorray.bud.lisp.lang.BudList;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudString;
import me.predatorray.bud.lisp.lang.Symbol;
import me.predatorray.bud.lisp.test.AbstractInterpreterTest;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

public class BudInterpreterTest extends AbstractInterpreterTest {

    @Test
    public void testNumber1() throws Exception {
        assertInterpretCorrectly(new BudNumber(BigDecimal.ONE), "number1.bud");
    }

    @Test
    public void testCharacterNewline() throws Exception {
        assertInterpretCorrectly(new BudCharacter('\n'), "character-newline.bud");
    }

    @Test
    public void testHelloWorld() throws Exception {
        assertInterpretCorrectly(new BudString("Hello, world!"), "hello-world.bud");
    }

    @Test
    public void testOnePlusOne() throws Exception {
        assertInterpretCorrectly(new BudNumber(new BigDecimal(2)), "one-plus-one.bud");
    }

    @Test
    public void testSumOneToFive() throws Exception {
        assertInterpretCorrectly(new BudNumber(new BigDecimal(15)), "sum-one-to-five.bud");
    }

    @Test
    public void testLastSymbol() throws Exception {
        assertInterpretCorrectly(new Symbol("foobar"), "last-symbol.bud");
    }

    @Test
    public void testLambdaAbs() throws Exception {
        assertInterpretCorrectly(new BudNumber(new BigDecimal(100)), "abs.bud");
    }

    @Test
    public void testNegNumericFunction() throws Exception {
        assertInterpretCorrectly(new BudNumber(new BigDecimal(-15)), "neg-function.bud");
    }

    @Test
    public void testFactorial() throws Exception {
        assertInterpretCorrectly(new BudNumber(new BigDecimal(120)), "factorial.bud");
    }

    @Test
    public void testSumOfNumberList() throws Exception {
        assertInterpretCorrectly(new BudNumber(new BigDecimal(15)), "sum-of-number-list.bud");
    }

    @Test
    public void testDoubleNumberList() throws Exception {
        assertInterpretCorrectly(new BudList(null, Arrays.<BudObject>asList(
                new BudNumber(new BigDecimal(2)),
                new BudNumber(new BigDecimal(4)),
                new BudNumber(new BigDecimal(6)))), "double-number-list.bud");
    }
}
