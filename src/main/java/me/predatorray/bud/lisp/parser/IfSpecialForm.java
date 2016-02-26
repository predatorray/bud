package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.Validation;

public class IfSpecialForm extends CompoundExpression {

    private final Expression test;
    private final Expression consequent;
    private final Expression alternate;

    public IfSpecialForm(Expression test, Expression consequent, Expression alternate, LeftParenthesis leading) {
        super(leading, "if", test, consequent, alternate);
        this.test = Validation.notNull(test);
        this.consequent = Validation.notNull(consequent);
        this.alternate = Validation.notNull(alternate);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public BudObject evaluate(Environment environment, Evaluator evaluator) {
        BudObject tested = evaluator.evaluate(test, environment);
        if (!BudBoolean.FALSE.equals(tested)) { // any object not #f is treated as true
            return evaluator.evaluate(consequent, environment);
        } else {
            return evaluator.evaluate(alternate, environment);
        }
    }

    public Expression getTest() {
        return test;
    }

    public Expression getConsequent() {
        return consequent;
    }

    public Expression getAlternate() {
        return alternate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IfSpecialForm that = (IfSpecialForm) o;

        if (!test.equals(that.test)) return false;
        if (!consequent.equals(that.consequent)) return false;
        return alternate.equals(that.alternate);
    }

    @Override
    public int hashCode() {
        int result = test.hashCode();
        result = 31 * result + consequent.hashCode();
        result = 31 * result + alternate.hashCode();
        return result;
    }
}
