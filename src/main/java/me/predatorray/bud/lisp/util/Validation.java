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
