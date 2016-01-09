package me.predatorray.bud.lisp.lexer;

public final class StringEscapeUtils {

    private StringEscapeUtils() {}

    /**
     * Escape the characters in a string.
     *
     * <p>For example, given a string <code>x\y"</code>, the output will be <code>x\\y\"</code></p>
     *
     * @param str a string to be escaped
     * @return a string with escaped values, <code>null</code> if input is <code>null</code>.
     */
    public static String escape(String str) {
        if (str == null) {
            return null;
        }
        return str; // TODO
    }

    /**
     * Unescape the characters in a string.
     *
     * <p>For example, given a string <code>\'\"</code>, the output will be <code>'"</code></p>
     *
     * @param str a string to be unescaped
     * @return a unescaped string <code>null</code> if input is <code>null</code>.
     */
    public static String unescape(String str) {
        if (str == null) {
            return null;
        }
        return str; // TODO
    }
}
