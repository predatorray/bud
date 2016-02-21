package me.predatorray.bud.lisp.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void testJoin1() throws Exception {
        String join = StringUtils.join(Arrays.asList("1", "2"), "-");
        assertEquals("1-2", join);
    }

    @Test
    public void testJoin2() throws Exception {
        String join = StringUtils.join(Arrays.asList("a", "b"), null);
        assertEquals("anullb", join);
    }

    @Test
    public void testJoin3() throws Exception {
        String join = StringUtils.join(Collections.singletonList("a"), null);
        assertEquals("a", join);
    }

    @Test
    public void testQuote1() throws Exception {
        String quoted = StringUtils.quote("abc");
        assertEquals("\"abc\"", quoted);
    }

    @Test
    public void testQuote2() throws Exception {
        String quoted = StringUtils.quote("a\n\tc");
        assertEquals("\"a\\n\\tc\"", quoted);
    }
}
