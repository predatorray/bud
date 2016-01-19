package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.IdentifierToken;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.lexer.RightParenthesis;
import me.predatorray.bud.lisp.lexer.TextLocation;
import me.predatorray.bud.lisp.lexer.Token;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@Ignore
public class ParserTest {

    private static final TextLocation DUMMY_LOCATION = new TextLocation(1, 1);

    private void assertMatches(Datum expected, List<? extends Token> input) {
        Parser parser = new Parser();
        Datum actual = parser.parse(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testParser1() throws Exception {
        // ( foobar )
        LeftParenthesis t0 = new LeftParenthesis(DUMMY_LOCATION);
        IdentifierToken t1 = new IdentifierToken("foobar", DUMMY_LOCATION);
        RightParenthesis t2 = new RightParenthesis(DUMMY_LOCATION);
        List<Token> input = Arrays.asList(t0, t1, t2);
        Datum expected = new ListDatum(Collections.singletonList(new Variable(t1)), t0);
        assertMatches(expected, input);
    }
}
