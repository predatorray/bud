/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Wenhao Ji
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
