package me.predatorray.bud.lisp.lang;

import me.predatorray.bud.lisp.util.Validation;

import java.util.Collections;
import java.util.List;

public class BudList implements BudObject {

    public static final BudList EMPTY_LIST = new BudList(null, Collections.<BudObject>emptyList());

    private final BudType elementType;
    private final List<BudObject> elements;

    private final BudType listType;

    public BudList(BudType elementType, List<BudObject> elements) {
        this.elementType = elementType;
        this.elements = Validation.notNull(elements);

        this.listType = new ListType(elementType);
    }

    @Override
    public BudType getType() {
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
}
