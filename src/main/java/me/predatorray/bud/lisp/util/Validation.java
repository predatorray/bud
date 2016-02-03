package me.predatorray.bud.lisp.util;

import java.util.Collection;

public final class Validation {

    public static <T> T notNull(T object) {
        return notNull(object, null);
    }

    public static <T> T notNull(T object, String message) {
        if (object != null) {
            return object;
        }
        if (message != null) {
            throw new NullPointerException(message);
        } else {
            throw new NullPointerException();
        }
    }

    public static <T extends Collection<?>> T notEmpty(T collection) {
        return notEmpty(collection, null);
    }

    public static <T extends Collection<?>> T notEmpty(T collection, String message) {
        if (collection == null) {
            throw new NullPointerException(message);
        }
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return collection;
    }
}
