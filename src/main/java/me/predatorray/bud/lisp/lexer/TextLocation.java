package me.predatorray.bud.lisp.lexer;

public class TextLocation {

    private final int line;
    private final int column;

    public TextLocation(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TextLocation that = (TextLocation) o;

        return (line == that.line) && (column == that.column);
    }

    @Override
    public int hashCode() {
        int result = line;
        result = 31 * result + column;
        return result;
    }
}
