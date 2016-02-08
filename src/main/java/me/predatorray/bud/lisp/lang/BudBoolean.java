package me.predatorray.bud.lisp.lang;

public enum BudBoolean implements BudObject {

    TRUE(true), FALSE(false);

    public static BudBoolean valueOf(boolean bool) {
        return bool ? TRUE : FALSE;
    }

    private final boolean value;

    BudBoolean(boolean boolValue) {
        this.value = boolValue;
    }

    @Override
    public BudType getType() {
        return BudType.BOOLEAN;
    }

    public boolean isTrue() {
        return value;
    }
}
