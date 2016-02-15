package me.predatorray.bud.lisp.lang;

import org.junit.Test;

import static org.junit.Assert.*;

public class ListTypeTest {

    @Test
    public void testListTypeEquals1() {
        ListType listTypeOfUnknown1 = new ListType(null);
        ListType listTypeOfUnknown2 = new ListType(null);
        assertTrue(listTypeOfUnknown1.equals(listTypeOfUnknown2));
    }

    @Test
    public void testListTypeEquals2() {
        ListType listTypeOfNumber1 = new ListType(BudType.NUMBER);
        ListType listTypeOfNumber2 = new ListType(BudType.NUMBER);
        assertTrue(listTypeOfNumber1.equals(listTypeOfNumber2));
    }

    @Test
    public void testListTypeEquals3() {
        ListType listTypeOfNumber1 = new ListType(null);
        ListType listTypeOfNumber2 = new ListType(BudType.NUMBER);
        assertFalse(listTypeOfNumber1.equals(listTypeOfNumber2));
    }
}