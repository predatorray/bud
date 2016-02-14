package me.predatorray.bud.lisp.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class StringEscapeUtils {

    private static final Map<Character, Character> ESCAPE_SEQUENCE_MAPPING = new HashMap<>();
    static {
        ESCAPE_SEQUENCE_MAPPING.put('n', '\n');
        ESCAPE_SEQUENCE_MAPPING.put('t', '\t');
        ESCAPE_SEQUENCE_MAPPING.put('b', '\b');
        ESCAPE_SEQUENCE_MAPPING.put('f', '\f');
        ESCAPE_SEQUENCE_MAPPING.put('r', '\r');
        ESCAPE_SEQUENCE_MAPPING.put('\"', '\"');
        ESCAPE_SEQUENCE_MAPPING.put('\'', '\'');
        ESCAPE_SEQUENCE_MAPPING.put('\\', '\\');
    }

    private static final Map<Character, Character> OPPOSITE_ESCAPE_SEQUENCE_MAPPING = new HashMap<>();
    static {
        for (Map.Entry<Character, Character> entry : ESCAPE_SEQUENCE_MAPPING.entrySet()) {
            OPPOSITE_ESCAPE_SEQUENCE_MAPPING.put(entry.getValue(), entry.getKey());
        }
    }

    private static final Set<Character> ESCAPE_SEQUENCES = ESCAPE_SEQUENCE_MAPPING.keySet();

    public static boolean isEscapeSequence(char c) {
        return ESCAPE_SEQUENCES.contains(c);
    }

    public static Character unescape(char c) {
        return ESCAPE_SEQUENCE_MAPPING.get(c);
    }

    public static Character escape(char c) {
        return OPPOSITE_ESCAPE_SEQUENCE_MAPPING.get(c);
    }

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
        StringBuilder escaped = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            Character escapedChar = escape(c);
            if (escapedChar == null) {
                escaped.append(c);
            } else {
                escaped.append('\\').append(escapedChar);
            }
        }
        return escaped.toString();
    }

    private StringEscapeUtils() {}
}
