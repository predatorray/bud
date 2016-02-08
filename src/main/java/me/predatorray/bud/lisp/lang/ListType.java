package me.predatorray.bud.lisp.lang;

public class ListType implements BudType {

    private final BudType elementType;

    public ListType(BudType elementType) {
        this.elementType = elementType;
    }

    public BudType getElementType() {
        return elementType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListType that = (ListType) o;

        return elementType.equals(that.elementType);
    }

    @Override
    public int hashCode() {
        return elementType.hashCode();
    }

    @Override
    public Category getCategory() {
        return Category.LIST;
    }
}
