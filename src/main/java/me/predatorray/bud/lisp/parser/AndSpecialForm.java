package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.Validation;

import java.util.List;

public class AndSpecialForm extends CompoundExpression {

    private final List<Expression> tests;

    public AndSpecialForm(List<Expression> tests, LeftParenthesis leading) {
        super(leading, "and", tests);
        this.tests = Validation.notNull(tests);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public BudObject evaluate(Environment environment, Evaluator evaluator) {
        BudObject eachTested = BudBoolean.TRUE;
        for (Expression test : tests) {
            eachTested = evaluator.evaluate(test, environment);
            if (BudBoolean.FALSE.equals(eachTested)) {
                return eachTested;
            }
        }
        return eachTested;
    }

    public List<Expression> getTests() {
        return tests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AndSpecialForm that = (AndSpecialForm) o;

        return tests.equals(that.tests);
    }

    @Override
    public int hashCode() {
        return tests.hashCode();
    }

    @Override
    public String toString() {
        return "";
    }
}
