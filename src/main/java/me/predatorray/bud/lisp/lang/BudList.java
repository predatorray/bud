package me.predatorray.bud.lisp.lang;

import me.predatorray.bud.lisp.util.Validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BudList implements BudObject {

    public static final BudList EMPTY_LIST = new BudList(null, Collections.<BudObject>emptyList());

    private final BudType elementType;
    private final List<BudObject> elements;
    private final int size;

    private final ListType listType;

    public BudList(BudType elementType, List<BudObject> elements) {
        this.elementType = elementType;
        this.elements = new ArrayList<>(Validation.notNull(elements));
        this.size = this.elements.size();

        this.listType = new ListType(elementType);
    }

    public int getSize() {
        return size;
    }

    public List<BudObject> getElements() {
        return elements;
    }

    @Override
    public ListType getType() {
        return listType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudList budList = (BudList) o;

        if (elementType != null ? !elementType.equals(budList.elementType) : budList.elementType != null) return false;
        return elements.equals(budList.elements);
    }

    @Override
    public int hashCode() {
        int result = elementType != null ? elementType.hashCode() : 0;
        result = 31 * result + elements.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return elements.toString() + ": " + elementType;
    }
}
