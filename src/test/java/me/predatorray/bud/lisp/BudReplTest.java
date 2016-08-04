package me.predatorray.bud.lisp;

import me.predatorray.bud.lisp.test.NullWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class BudReplTest {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

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

    private String appendNewLine(String ...lines) {
        StringBuilder lineAppended = new StringBuilder();
        for (String line : lines) {
            lineAppended.append(line).append(LINE_SEPARATOR);
        }
        return lineAppended.toString();
    }

    @Test
    public void testRepl1() throws Exception {
        String output = readEvaluateAndPrint("\"abc\"");
        assertEquals(appendNewLine("\"abc\""), output);
    }

    @Test
    public void testRepl2() throws Exception {
        readEvaluateAndPrint("unknown");
        String output = readEvaluateAndPrint("\"abc\"");
        assertEquals(appendNewLine("\"abc\""), output);
    }

    @Test
    public void testRepl3() throws Exception {
        String output = readEvaluateAndPrint("(+ 1 1) 3");
        assertEquals(appendNewLine("2", "3"), output);
    }

    @Test
    public void testRepl4() throws Exception {
        String output = readEvaluateAndPrint("(+ 1 1) (\n" +
                "- 1 1)\n");
        assertEquals(appendNewLine("2", "0"), output);
    }
}
