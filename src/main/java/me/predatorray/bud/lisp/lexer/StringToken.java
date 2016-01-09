package me.predatorray.bud.lisp.lexer;

public class StringToken implements Token {

    private final String stringValue;
    private final String doubleQuoted;

    public StringToken(String stringValue) {
        if (stringValue == null) {
            throw new NullPointerException();
        }

        this.stringValue = stringValue;
        this.doubleQuoted = "\"" + StringEscapeUtils.escape(stringValue) + "\"";
    }

    public TokenLocation getLocation() {
        return null;
    }

    public void accept(LispTokenVisitor visitor) {
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
