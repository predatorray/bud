package me.predatorray.bud.lisp.lexer;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Ignore
public class LexerTest {

    private void assertMatches(List<? extends Token> expectedTokens, String input) {
        Lexer lexer = new Lexer(input);
        List<Token> actual = new LinkedList<Token>();
        for (Token token : lexer) {
            actual.add(token);
        }
        Assert.assertEquals(expectedTokens, actual);
    }

    @Test
    public void testLexer1() {
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
}
