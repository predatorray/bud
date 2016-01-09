package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.Token;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

@Ignore
public class ParserTest {

    private void assertMatches(Datum expected, List<? extends Token> input) {
        Parser parser = new Parser();
        Datum actual = parser.parse(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testParser1() throws Exception {
        // TODO
    }
}
