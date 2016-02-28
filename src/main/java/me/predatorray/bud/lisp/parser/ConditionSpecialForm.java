package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lang.Function;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.Validation;

import java.util.Collections;
import java.util.List;

public class ConditionSpecialForm extends CompoundExpression {

    private final List<ConditionClause> clauses;
    private final Expression elseExpression;

    public ConditionSpecialForm(List<ConditionClause> clauses, Expression elseExpression, LeftParenthesis leading) {
        super(leading, "cond", clauses);
        this.clauses = Validation.notEmpty(clauses);
        this.elseExpression = elseExpression;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public BudObject evaluate(Environment environment, Evaluator evaluator) {
        for (ConditionClause clause : clauses) {
            Expression test = clause.getTest();
            BudObject tested = evaluator.evaluate(test, environment);
            if (BudBoolean.FALSE.equals(tested)) {
                continue;
            }

            if (clause.hasRecipient()) {
                Expression recipient = clause.getRecipient();
                BudObject recipientObj = evaluator.evaluate(recipient, environment);
                if (!BudType.Category.FUNCTION.equals(recipientObj.getType().getCategory())) {
                    throw new NotApplicableException(recipient);
                }
                Function recipientFunction = (Function) recipientObj;
                recipientFunction.inspect(Collections.singletonList(tested.getType()));
                return recipientFunction.apply(Collections.singletonList(tested));
            } else {
                Expression consequent = clause.getConsequent();
                return evaluator.evaluate(consequent, environment);
            }
        }
        if (elseExpression == null) {
            throw new EvaluatingException("all clauses in cond are evaluated to false values and " +
                    "no else-clause is found",
                    this);
        }
        return evaluator.evaluate(elseExpression, environment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConditionSpecialForm that = (ConditionSpecialForm) o;

        if (!clauses.equals(that.clauses)) return false;
        return elseExpression != null ? elseExpression.equals(that.elseExpression) : that.elseExpression == null;
    }

    @Override
    public int hashCode() {
        int result = clauses.hashCode();
        result = 31 * result + (elseExpression != null ? elseExpression.hashCode() : 0);
        return result;
    }

    public List<ConditionClause> getClauses() {
        return clauses;
    }

    public Expression getElseExpression() {
        return elseExpression;
    }
}
