package me.predatorray.bud.lisp.lexer;

abstract class ConstantToken implements Token {

    private final TextLocation location;
    private final String textForm;

    protected ConstantToken(TextLocation location, String textForm) {
        this.location = location;
        this.textForm = textForm;
    }

    @Override
    public TextLocation getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return textForm;
    }
}
