package me.predatorray.bud.lisp.test;

import me.predatorray.bud.lisp.BudInterpreter;
import me.predatorray.bud.lisp.lang.BudObject;
import org.junit.Assert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractInterpreterTest extends AbstractBudLispTest {

    private final BudInterpreter interpreter;

    public AbstractInterpreterTest() {
        this(new BudInterpreter());
    }

    public AbstractInterpreterTest(BudInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    protected final void assertInterpretCorrectly(BudObject expected, String sourceClasspath)
            throws URISyntaxException, IOException, InterruptedException {
        Path path = Paths.get(this.getClass().getResource(sourceClasspath).toURI());
        {
            BudObject actual = interpreter.interpret(path);
            Assert.assertEquals(expected, actual);
        }
        {
            BudObject actual = interpreter.interpretInterruptibly(path);
            Assert.assertEquals(expected, actual);
        }
    }
}
