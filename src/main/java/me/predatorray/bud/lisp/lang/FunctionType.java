package me.predatorray.bud.lisp.lang;

import me.predatorray.bud.lisp.util.Validation;

public class FunctionType implements BudType {

    private final Function function;

    public FunctionType(Function function) {
        this.function = Validation.notNull(function);
    }

    public Function getFunction() {
        return function;
    }

    @Override
    public Category getCategory() {
        return Category.FUNCTION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FunctionType that = (FunctionType) o;

        return function.equals(that.function);
    }

    @Override
    public int hashCode() {
        return function.hashCode();
    }
}
