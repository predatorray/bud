package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.IdentifierToken;
import me.predatorray.bud.lisp.lexer.SingleQuoteToken;
import me.predatorray.bud.lisp.lexer.TextLocation;
import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.parser.datum.CompoundDatum;
import me.predatorray.bud.lisp.parser.datum.Datum;
import me.predatorray.bud.lisp.parser.datum.SymbolDatum;
import me.predatorray.bud.lisp.test.AbstractBudLispTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParserTest extends AbstractBudLispTest {

    private void assertMatches(Expression expected, List<? extends Token> input) {
        Parser parser = new Parser();
        List<Expression> actualExpressions = parser.parse(input);
        Assert.assertNotNull("only one expression is expected", actualExpressions);
        Assert.assertEquals("only one expression is expected", 1, actualExpressions.size());
        Assert.assertEquals(input.toString(), expected, actualExpressions.get(0));
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
    public void testParseQuote1() throws Exception {
        // ( quote a )
        IdentifierToken quote = new IdentifierToken("quote", DUMMY_LOCATION);
        IdentifierToken a = new IdentifierToken("a", DUMMY_LOCATION);
        List<Token> input = Arrays.asList(LP, quote, a, RP);

        Expression expected = new QuoteSpecialForm(new SymbolDatum(new IdentifierToken("a", DUMMY_LOCATION)), LP);
        assertMatches(expected, input);
    }

    @Test
    public void testParseQuote2() throws Exception {
        // 'a
        SingleQuoteToken quote = new SingleQuoteToken(DUMMY_LOCATION);
        IdentifierToken a = new IdentifierToken("a", DUMMY_LOCATION);
        List<Token> input = Arrays.asList(quote, a);

        Expression expected = new QuoteSpecialForm(new SymbolDatum(new IdentifierToken("a", DUMMY_LOCATION)), LP);
        assertMatches(expected, input);
    }

    @Test
    public void testParseQuote3() throws Exception {
        // '(a b)
        SingleQuoteToken quote = new SingleQuoteToken(DUMMY_LOCATION);
        IdentifierToken a = new IdentifierToken("a", DUMMY_LOCATION);
        IdentifierToken b = new IdentifierToken("b", DUMMY_LOCATION);
        List<Token> input = Arrays.asList(quote, LP, a, b, RP);

        Expression expected = new QuoteSpecialForm(new CompoundDatum(Arrays.<Datum>asList(
                new SymbolDatum(new IdentifierToken("a", DUMMY_LOCATION)),
                new SymbolDatum(new IdentifierToken("b", DUMMY_LOCATION))), LP), LP);
        assertMatches(expected, input);
    }

    @Test
    public void testParseQuote4() throws Exception {
        // ''()
        SingleQuoteToken quote = new SingleQuoteToken(DUMMY_LOCATION);
        List<Token> input = Arrays.<Token>asList(quote, quote, LP, RP);

        Expression expected = new QuoteSpecialForm(new CompoundDatum(Arrays.asList(
                new SymbolDatum(new IdentifierToken("quote", DUMMY_LOCATION)),
                new CompoundDatum(Collections.<Datum>emptyList(), LP)), LP), LP);
        assertMatches(expected, input);
    }

    @Test
    public void testParseQuote5() throws Exception {
        // (a '(b '(c) 'd))
        SingleQuoteToken quote = new SingleQuoteToken(DUMMY_LOCATION);
        IdentifierToken a = new IdentifierToken("a", DUMMY_LOCATION);
        IdentifierToken b = new IdentifierToken("b", DUMMY_LOCATION);
        IdentifierToken c = new IdentifierToken("c", DUMMY_LOCATION);
        IdentifierToken d = new IdentifierToken("d", DUMMY_LOCATION);
        List<Token> input = Arrays.asList(LP, a, quote, lp(1), b, quote, lp(2), c, rp(1), quote, d, rp(2), RP);

        Expression expected = newProcedureCall(newVariable("a"),
                newQuoteSpecialForm(newCompoundDatum(lp(1),
                        newSymbolDatum("b"), // b
                        newCompoundDatum(newSymbolDatum("quote"), newCompoundDatum(lp(2), newSymbolDatum("c"))), // '(c)
                        newCompoundDatum(newSymbolDatum("quote"), newSymbolDatum("d"))))); // 'd
        assertMatches(expected, input);
    }

    @Test
    public void testParseQuote6() throws Exception {
        // '''a
        SingleQuoteToken[] quotes = new SingleQuoteToken[] {
                new SingleQuoteToken(new TextLocation(1, 1)),
                new SingleQuoteToken(new TextLocation(1, 2)),
                new SingleQuoteToken(new TextLocation(1, 3))
        };
        IdentifierToken a = new IdentifierToken("a", DUMMY_LOCATION);
        List<Token> input = Arrays.asList(quotes[0], quotes[1], quotes[2], a);

        Expression expected = newQuoteSpecialForm(
                newCompoundDatum(lp(2),
                        newSymbolDatum("quote", new TextLocation(1, 2)),
                        newCompoundDatum(lp(3),
                                newSymbolDatum("quote", new TextLocation(1, 3)),
                                newSymbolDatum("a"))));
        assertMatches(expected, input);
    }

    @Test
    public void testParseLambda1() throws Exception {
        // (lambda (arg1 arg2) (+ arg1 arg2))
        IdentifierToken lambda = new IdentifierToken("lambda", DUMMY_LOCATION);
        IdentifierToken arg1 = new IdentifierToken("arg1", DUMMY_LOCATION);
        IdentifierToken arg2 = new IdentifierToken("arg2", DUMMY_LOCATION);
        IdentifierToken plus = new IdentifierToken("+", DUMMY_LOCATION);
        List<Token> input = Arrays.asList(LP, lambda, LP, arg1, arg2, RP, LP, plus, arg1, arg2, RP, RP);

        Variable var1 = new Variable(arg1);
        Variable var2 = new Variable(arg2);
        Expression addTwoArgs = new ProcedureCall(new Variable(plus), Arrays.asList(var1, var2), LP);
        Expression expected = new LambdaExpression(Arrays.asList(var1, var2),
                Collections.<Definition>emptyList(), addTwoArgs, LP);
        assertMatches(expected, input);
    }
}
