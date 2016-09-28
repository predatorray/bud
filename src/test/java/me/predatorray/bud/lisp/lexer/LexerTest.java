package me.predatorray.bud.lisp.lexer;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LexerTest {

    private void assertMatches(List<? extends Token> expectedTokens, String input) {
        Lexer lexer = new Lexer(input);
        List<Token> actual = new LinkedList<Token>();
        for (Token token : lexer) {
            actual.add(token);
        }
        Assert.assertArrayEquals(expectedTokens.toArray(), actual.toArray());
    }

    @Test
    public void testNormalApplication() {
        assertMatches(Arrays.asList(
                new LeftParenthesis(new TextLocation(1, 1)),
                new IdentifierToken("+", new TextLocation(1, 2)),
                new Atmosphere(new TextLocation(1, 3)),
                new IdentifierToken("a", new TextLocation(1, 4)),
                new Atmosphere(new TextLocation(1, 5)),
                new IdentifierToken("b", new TextLocation(1, 6)),
                new RightParenthesis(new TextLocation(1, 7))),
                "(+ a b)");
    }

    @Test
    public void testApplyingEscapedString() {
        assertMatches(Arrays.asList(
                new LeftParenthesis(new TextLocation(1, 1)),
                new IdentifierToken("foobar", new TextLocation(1, 2)),
                new Atmosphere(new TextLocation(1, 8)),
                new StringToken("str str\n\t\b\f\r\"\'\\", new TextLocation(1, 9)),
                new RightParenthesis(new TextLocation(1, 34))),
                "(foobar \"str str\\n\\t\\b\\f\\r\\\"\\\'\\\\\")");
    }

    @Test
    public void testMultipleIdentifiers() {
        assertMatches(Arrays.asList(
                new Atmosphere(new TextLocation(1, 1)),
                new NumberToken(new BigDecimal(123), new TextLocation(2, 1)),
                new Atmosphere(new TextLocation(2, 4)),
                new SingleQuoteToken(new TextLocation(2, 5)),
                new IdentifierToken("abc", new TextLocation(2, 6)),
                new Atmosphere(new TextLocation(2, 9)),
                new Atmosphere(new TextLocation(2, 10)),
                new IdentifierToken("xyz", new TextLocation(2, 11))),
                "\n123 'abc  xyz");
    }

    @Test
    public void testBoolean() {
        assertMatches(Arrays.asList(new BooleanToken(true, new TextLocation(1, 1)),
                new Atmosphere(new TextLocation(1, 3)),
                new BooleanToken(false, new TextLocation(1, 4))),
                "#t #f");
    }

    @Test
    public void testCharacter() {
        assertMatches(Collections.singletonList(new CharacterToken((char) 1, new TextLocation(1, 1))),
                "#\\001");
    }

    @Test
    public void testNumber() {
        assertMatches(Collections.singletonList(new NumberToken(new BigDecimal("0.123"), new TextLocation(1, 1))),
                ".123");
    }

    @Test
    public void testIdentifierContainingNumber() {
        assertMatches(Collections.singletonList(new IdentifierToken("fib0", new TextLocation(1, 1))),
                "fib0");
    }
}
