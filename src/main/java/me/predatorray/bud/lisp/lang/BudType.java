package me.predatorray.bud.lisp.lang;

public interface BudType {

    Category getCategory();

    enum Category {
        PRIMITIVE, SYMBOL, ENV, LIST, FUNCTION, OTHER
    }

    BudType BOOLEAN = new BudType() {
        @Override
        public Category getCategory() {
            return Category.PRIMITIVE;
        }

        @Override
        public String toString() {
            return "BOOLEAN";
        }
    };

    BudType NUMBER = new BudType() {
        @Override
        public Category getCategory() {
            return Category.PRIMITIVE;
        }

        @Override
        public String toString() {
            return "NUMBER";
        }
    };

    BudType CHARACTER = new BudType() {
        @Override
        public Category getCategory() {
            return Category.PRIMITIVE;
        }

        @Override
        public String toString() {
            return "CHARACTER";
        }
    };

    BudType STRING = new BudType() {
        @Override
        public Category getCategory() {
            return Category.PRIMITIVE;
        }

        @Override
        public String toString() {
            return "STRING";
        }
    };

    BudType SYMBOL = new BudType() {
        @Override
        public Category getCategory() {
            return Category.SYMBOL;
        }

        @Override
        public String toString() {
            return "SYMBOL";
        }
    };

    BudType ENV = new BudType() {
        @Override
        public Category getCategory() {
            return Category.ENV;
        }

        @Override
        public String toString() {
            return "ENV";
        }
    };
}
