package me.predatorray.bud.lisp.util;

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
}
