/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Wenhao Ji
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
