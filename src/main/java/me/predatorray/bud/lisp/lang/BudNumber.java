package me.predatorray.bud.lisp.lang;

import me.predatorray.bud.lisp.util.Validation;

import java.math.BigDecimal;

public class BudNumber implements BudObject {

    private final BigDecimal value;

    public BudNumber(BigDecimal value) {
        this.value = Validation.notNull(value);
    }

    @Override
    public BudType getType() {
        return BudType.NUMBER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudNumber budNumber = (BudNumber) o;

        return value.equals(budNumber.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public BigDecimal getValue() {
        return value;
    }
}
