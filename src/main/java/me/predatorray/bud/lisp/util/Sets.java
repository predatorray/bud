package me.predatorray.bud.lisp.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Sets {

    @SafeVarargs
    public static <T> Set<T> asSet(T ...ts) {
        Validation.notNull(ts, "sets must not be null");
        return Collections.unmodifiableSet(new HashSet<T>(Arrays.asList(ts)));
    }

    public static <T> Set<T> union(Collection<? extends T> collection1, Collection<? extends T> collection2) {
        Validation.notNull(collection1);
        Validation.notNull(collection2);

        int capacity = collection1.size() + collection2.size();
        Set<T> union = new HashSet<T>(capacity);
        union.addAll(collection1);
        union.addAll(collection2);
        return union;
    }
}
