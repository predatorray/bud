package me.predatorray.bud.lisp.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringEscapeUtilsTest {

    @Test
    public void testEscape1() {
        String str = "\n\t\b\f\r\"\'\\";
        String escaped = StringEscapeUtils.escape(str);
        assertEquals("\\n\\t\\b\\f\\r\\\"\\\'\\\\", escaped);
    }

    @Test
    public void testEscape2() {
        String str = "abcdef012345";
        String escaped = StringEscapeUtils.escape(str);
        assertEquals(str, escaped);
    }

    @Test
    public void testEscape3() {
        Character escaped = StringEscapeUtils.escape('\n');
        assertEquals(Character.valueOf('n'), escaped);
    }

    @Test
    public void testEscape4() {
        Character escaped = StringEscapeUtils.escape(' ');
        assertNull(escaped);
    }

    @Test
    public void testIsEscapeSequence() throws Exception {
        String escapeSequences = "ntbfr\"\'\\";
        for (char c : escapeSequences.toCharArray()) {
            assertTrue(StringEscapeUtils.isEscapeSequence(c));
        }
    }

    @Test
    public void testUnescape1() throws Exception {
        Character unescaped = StringEscapeUtils.unescape('n');
        assertEquals(Character.valueOf('\n'), unescaped);
    }

    @Test
    public void testUnescape2() throws Exception {
        Character unescaped = StringEscapeUtils.unescape('1');
        assertNull(unescaped);
    }
}
