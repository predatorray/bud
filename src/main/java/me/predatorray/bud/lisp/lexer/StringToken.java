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
package me.predatorray.bud.lisp.lexer;

import me.predatorray.bud.lisp.util.StringEscapeUtils;
import me.predatorray.bud.lisp.util.Validation;

public class StringToken implements Token {

    private final String stringValue;
    private final String doubleQuoted;
    private final TextLocation location;

    public StringToken(String stringValue, TextLocation location) {
        this.location = location;
        this.stringValue = Validation.notNull(stringValue);
        this.doubleQuoted = "\"" + StringEscapeUtils.escape(stringValue) + "\"";
    }

    @Override
    public TextLocation getLocation() {
        return location;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public String getStringValue() {
        return stringValue;
    }

    @Override
    public String toString() {
        return doubleQuoted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringToken that = (StringToken) o;

        if (!stringValue.equals(that.stringValue)) return false;
        return location != null ? location.equals(that.location) : that.location == null;

    }

    @Override
    public int hashCode() {
        int result = stringValue.hashCode();
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
