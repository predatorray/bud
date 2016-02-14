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
