package me.predatorray.bud.lisp.util;

import java.util.Collection;

public class StringUtils {

    public static String join(Collection<?> objects, Object separator) {
        StringBuilder joined = new StringBuilder();
        boolean first = true;
        for (Object object : objects) {
            if (first) {
                first = false;
            } else {
                joined.append(separator);
            }
            joined.append(object);
        }
        return joined.toString();
    }
}
