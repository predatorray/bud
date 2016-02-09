package me.predatorray.bud.lisp.lang;

import me.predatorray.bud.lisp.util.Validation;

public class BudString implements BudObject {

    private final String value;

    public BudString(String value) {
        this.value = Validation.notNull(value);
    }

    @Override
    public BudType getType() {
        return BudType.STRING;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudString budString = (BudString) o;

        return value.equals(budString.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
