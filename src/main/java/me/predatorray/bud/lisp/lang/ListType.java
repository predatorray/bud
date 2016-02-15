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

        ListType listType = (ListType) o;

        return elementType != null ? elementType.equals(listType.elementType) : listType.elementType == null;
    }

    @Override
    public int hashCode() {
        return elementType != null ? elementType.hashCode() : 0;
    }

    @Override
    public Category getCategory() {
        return Category.LIST;
    }

    @Override
    public String toString() {
        return "LIST<" + elementType + ">";
    }
}
