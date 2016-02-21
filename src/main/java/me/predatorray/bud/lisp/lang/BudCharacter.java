package me.predatorray.bud.lisp.lang;

public class BudCharacter implements BudObject {

    private final char c;

    public BudCharacter(char c) {
        this.c = c;
    }

    @Override
    public BudType getType() {
        return BudType.CHARACTER;
    }

    public char getValue() {
        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudCharacter that = (BudCharacter) o;

        return c == that.c;
    }

    @Override
    public int hashCode() {
        return (int) c;
    }

    @Override
    public String toString() {
        return "#\\" + c; // TODO escape
    }
}
