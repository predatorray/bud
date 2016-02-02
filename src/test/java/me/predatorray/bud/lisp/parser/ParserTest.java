package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.IdentifierToken;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.lexer.RightParenthesis;
import me.predatorray.bud.lisp.lexer.TextLocation;
import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.parser.datum.SymbolDatum;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Ignore
public class ParserTest {

    private static final TextLocation DUMMY_LOCATION = new TextLocation(1, 1);

    private static final LeftParenthesis LP = new LeftParenthesis(DUMMY_LOCATION);
    private static final RightParenthesis RP = new RightParenthesis(DUMMY_LOCATION);

    private void assertMatches(Expression expected, List<? extends Token> input) {
        Parser parser = new Parser();
        Expression actual = parser.parse(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testParser1() throws Exception {
        // ( foobar )
        IdentifierToken foobar = new IdentifierToken("foobar", DUMMY_LOCATION);
        List<Token> input = Arrays.asList(LP, foobar, RP);
        Expression expected = new ProcedureCall(new Variable(foobar), Collections.<Expression>emptyList(), LP);
        assertMatches(expected, input);
    }

    @Test
    public void testParser2() throws Exception {
        // ( ( foobar ) arg1, arg2 )
        IdentifierToken foobar = new IdentifierToken("foobar", DUMMY_LOCATION);
        IdentifierToken arg1 = new IdentifierToken("arg1", DUMMY_LOCATION);
        IdentifierToken arg2 = new IdentifierToken("arg2", DUMMY_LOCATION);
        List<Token> input = Arrays.asList(LP, LP, foobar, RP, arg1, arg2, RP);

        Expression expected = new ProcedureCall(
                new ProcedureCall(new Variable(foobar), Collections.<Expression>emptyList(), LP),
                Arrays.asList(new Variable(arg1), new Variable(arg2)), LP);
        assertMatches(expected, input);
    }

    @Test
    public void testParser3() throws Exception {
        // ( quote a )
        IdentifierToken quote = new IdentifierToken("quote", DUMMY_LOCATION);
        IdentifierToken a = new IdentifierToken("a", DUMMY_LOCATION);
        List<Token> input = Arrays.asList(LP, quote, a, RP);

        Expression expected = new QuoteSpecialForm(new SymbolDatum(new IdentifierToken("a", DUMMY_LOCATION)), LP);
        assertMatches(expected, input);
    }
}
