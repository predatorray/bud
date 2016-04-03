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

    public static <T> void that(boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }

    public static <T> T[] notEmpty(T[] arr) {
        return notEmpty(arr, null);
    }

    public static <T> T[] notEmpty(T[] arr, String message) {
        if (arr == null) {
            throw new NullPointerException(message);
        }
        if (arr.length == 0) {
            throw new IllegalArgumentException(message);
        }
        return arr;
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

    public static String notEmpty(String s) {
        return notEmpty(s, null);
    }

    public static String notEmpty(String s, String message) {
        if (s == null) {
            throw new NullPointerException(message);
        }
        if (s.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return s;
    }
}
