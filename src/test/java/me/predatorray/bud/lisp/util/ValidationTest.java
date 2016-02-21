package me.predatorray.bud.lisp.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class ValidationTest {

    @Test(expected = NullPointerException.class)
    public void testNotNull1() throws Exception {
        Validation.notNull(null);
    }

    @Test(expected = NullPointerException.class)
    public void testNotNull2() throws Exception {
        try {
            Validation.notNull(null, "message");
        } catch (Exception e) {
            Assert.assertEquals("message", e.getMessage());
            throw e;
        }
    }

    @Test
    public void testNotNull3() throws Exception {
        Validation.notNull(new Object(), "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThat1() throws Exception {
        Validation.that(false);
    }

    @Test
    public void testThat2() throws Exception {
        Validation.that(true);
    }

    @Test(expected = NullPointerException.class)
    public void testNotEmpty1() throws Exception {
        Validation.notEmpty(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEmpty2() throws Exception {
        Validation.notEmpty(Collections.emptyList());
    }

    @Test
    public void testNotEmpty3() throws Exception {
        Validation.notEmpty(Collections.singleton(new Object()));
    }
}