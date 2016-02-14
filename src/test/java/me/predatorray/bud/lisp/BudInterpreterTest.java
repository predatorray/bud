package me.predatorray.bud.lisp;

import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudString;
import me.predatorray.bud.lisp.lang.Symbol;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BudInterpreterTest {

    private final BudInterpreter interpreter = new BudInterpreter();

    private void assertInterpretCorrectly(BudObject expected, String sourceClasspath)
            throws URISyntaxException, IOException {
        Path path = Paths.get(this.getClass().getResource(sourceClasspath).toURI());
        BudObject actual = interpreter.execute(path);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testNumber1() throws Exception {
        assertInterpretCorrectly(new BudNumber(BigDecimal.ONE), "number1.bud");
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

    @Ignore("single quote and boolean tokens are currently not supported")
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
}
