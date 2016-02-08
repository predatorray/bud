package me.predatorray.bud.lisp.lang;

public interface BudType {

    Category getCategory();

    enum Category {
        PRIMITIVE, LIST, FUNCTION, OTHER
    }

    BudType BOOLEAN = new BudType() {
        @Override
        public Category getCategory() {
            return Category.PRIMITIVE;
        }
    };

    BudType NUMBER = new BudType() {
        @Override
        public Category getCategory() {
            return Category.PRIMITIVE;
        }
    };

    BudType STRING = new BudType() {
        @Override
        public Category getCategory() {
            return Category.PRIMITIVE;
        }
    };

    BudType SYMBOL = new BudType() {
        @Override
        public Category getCategory() {
            return Category.OTHER;
        }
    };

    BudType ENV = new BudType() {
        @Override
        public Category getCategory() {
            return Category.OTHER;
        }
    };
}
