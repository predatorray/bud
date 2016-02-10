package me.predatorray.bud.lisp.lexer;

import me.predatorray.bud.lisp.util.StringEscapeUtils;

public class StringToken implements Token {

    private final String stringValue;
    private final String doubleQuoted;
    private final TextLocation location;

    public StringToken(String stringValue, TextLocation location) {
        this.location = location;
        if (stringValue == null) {
            throw new NullPointerException();
        }

        this.stringValue = stringValue;
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
}
