package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.*;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.Validation;

import java.util.List;

public class OrSpecialForm extends CompoundExpression {

    private final List<Expression> tests;

    public OrSpecialForm(List<Expression> tests, LeftParenthesis leading) {
        super(leading, "or", tests);
        this.tests = Validation.notNull(tests);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public BudObject evaluate(Environment environment, Evaluator evaluator) {
        BudObject eachTested = BudBoolean.FALSE;
        for (Expression test : tests) {
            eachTested = evaluator.evaluate(test, environment);
            if (BudBoolean.isTrue(eachTested)) {
                return eachTested;
            }
        }
        return eachTested;
    }

    @Override
    public BudFuture evaluateAndGetBudFuture(Environment environment, Evaluator evaluator) {
        if (tests.isEmpty()) {
            return new CompletedBudFuture(BudBoolean.FALSE);
        }
        int last = tests.size() - 1;
        for (int i = 0; i < last; i++) {
            Expression test = tests.get(i);
            BudObject tested = evaluator.evaluate(test, environment);
            if (BudBoolean.isTrue(tested)) {
                return new CompletedBudFuture(tested);
            }
        }

        return new TailExpressionBudFuture(tests.get(last), environment, evaluator);
    }

    public List<Expression> getTests() {
        return tests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrSpecialForm that = (OrSpecialForm) o;

        return tests.equals(that.tests);
    }

    @Override
    public int hashCode() {
        return tests.hashCode();
    }
}
