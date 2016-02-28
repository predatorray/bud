package me.predatorray.bud.lisp.lang;

import me.predatorray.bud.lisp.util.Validation;

public enum BudBoolean implements BudObject {

    TRUE(true), FALSE(false);

    public static BudBoolean valueOf(boolean bool) {
        return bool ? TRUE : FALSE;
    }

    /**
     * Returns if the object is treated as true.
     * All objects that is not {@link BudBoolean#FALSE} are true.
     *
     * @param object a bud object
     * @throws NullPointerException if object is null
     * @return true if if the object is treated as true, otherwise false
     */
    public static boolean isTrue(BudObject object) {
        Validation.notNull(object);
        return !isFalse(object);
    }

    /**
     * Returns if the object is treated as falses.
     * All objects that is {@link BudBoolean#FALSE} are false.
     *
     * @param object a bud object
     * @throws NullPointerException if object is null
     * @return true if if the object is treated as false, otherwise false
     */
    public static boolean isFalse(BudObject object) {
        Validation.notNull(object);
        return BudBoolean.FALSE.equals(object);
    }

    private final boolean value;

    BudBoolean(boolean boolValue) {
        this.value = boolValue;
    }

    @Override
    public BudType getType() {
        return BudType.BOOLEAN;
    }

    @Override
    public String toString() {
        return value ? "#t" : "#f";
    }
}
