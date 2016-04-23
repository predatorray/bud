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
package me.predatorray.bud.lisp.lang;

import me.predatorray.bud.lisp.util.Validation;

public enum BudBoolean implements BudObject {

    TRUE(true), FALSE(false);

    public static BudBoolean valueOf(boolean bool) {
        return bool ? TRUE : FALSE;
    }

    /**
     * Returns if the object is treated as true.
     * All objects that is not {@link BudBoolean#FALSE} are true.
     *
     * @param object a bud object
     * @throws NullPointerException if object is null
     * @return true if if the object is treated as true, otherwise false
     */
    public static boolean isTrue(BudObject object) {
        Validation.notNull(object);
        return !isFalse(object);
    }

    /**
     * Returns if the object is treated as falses.
     * All objects that is {@link BudBoolean#FALSE} are false.
     *
     * @param object a bud object
     * @throws NullPointerException if object is null
     * @return true if if the object is treated as false, otherwise false
     */
    public static boolean isFalse(BudObject object) {
        Validation.notNull(object);
        return BudBoolean.FALSE.equals(object);
    }

    private final boolean value;

    BudBoolean(boolean boolValue) {
        this.value = boolValue;
    }

    @Override
    public BudType getType() {
        return BudType.BOOLEAN;
    }

    @Override
    public String toString() {
        return value ? "#t" : "#f";
    }
}
