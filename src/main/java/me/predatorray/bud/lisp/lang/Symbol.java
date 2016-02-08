package me.predatorray.bud.lisp.lang;

import me.predatorray.bud.lisp.util.Validation;

public class Symbol implements BudObject {

    private final String name;

    public Symbol(String name) {
        this.name = Validation.notNull(name);
    }

    @Override
    public BudType getType() {
        return BudType.SYMBOL;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Symbol symbol = (Symbol) o;

        return name.equals(symbol.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
