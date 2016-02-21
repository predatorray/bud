package me.predatorray.bud.lisp;

import me.predatorray.bud.lisp.test.NullWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class BudReplTest {

    private BudRepl repl;

    @Before
    public void setUpFixtureOfRepl() {
        repl = new BudRepl(false);
    }

    private String readEvaluateAndPrint(String input) throws IOException {
        try (Reader reader = new StringReader(input);
             Writer writer = new StringWriter()) {
            repl.execute(reader, writer, new NullWriter());
            return writer.toString();
        }
    }

    @Test
    public void testRepl1() throws Exception {
        String output = readEvaluateAndPrint("\"abc\"");
        assertEquals("\"abc\"\n", output);
    }

    @Test
    public void testRepl2() throws Exception {
        readEvaluateAndPrint("unknown");
        String output = readEvaluateAndPrint("\"abc\"");
        assertEquals("\"abc\"\n", output);
    }

    @Test
    public void testRepl3() throws Exception {
        String output = readEvaluateAndPrint("(+ 1 1) 3");
        assertEquals("2\n3\n", output);
    }

    @Test
    public void testRepl4() throws Exception {
        String output = readEvaluateAndPrint("(+ 1 1) (\n" +
                "- 1 1)\n");
        assertEquals("2\n0\n", output);
    }
}
