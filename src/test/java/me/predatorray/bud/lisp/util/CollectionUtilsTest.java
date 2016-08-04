package me.predatorray.bud.lisp.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollectionUtilsTest {

    @Test
    public void testAsSet1() {
        Object[] objects = new Object [] {new Object(), new Object()};
        Set<Object> set = CollectionUtils.asSet(objects);
        assertEquals(2, set.size());
        for (Object object : objects) {
            assertTrue(set.contains(object));
        }
    }

    @Test
    public void testAsSet2() {
        Set<Object> set = CollectionUtils.asSet();
        assertEquals(0, set.size());
    }

    @Test
    public void testUnion1() {
        Collection<Object> collection1 = Arrays.asList(new Object(), new Object());
        Collection<Object> collection2 = Arrays.asList(new Object(), new Object(), new Object());
        Set<Object> union = CollectionUtils.union(collection1, collection2);
        assertEquals(5, union.size());
        for (Object o1 : collection1) {
            union.contains(o1);
        }
        for (Object o2 : collection2) {
            union.contains(o2);
        }
    }
}