package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.util.Validation;

public class ConditionClause {

    private final Expression test;
    private final Expression recipient;
    private final Expression consequent;

    public static ConditionClause newConditionClauseOfTestAlone(Expression test) {
        return new ConditionClause(test, null, null);
    }

    public static ConditionClause newConditionClauseOfConsequentExpression(Expression test, Expression consequent) {
        return new ConditionClause(test, consequent, null);
    }

    public static ConditionClause newConditionClauseOfRecipientExpression(Expression test, Expression recipient) {
        return new ConditionClause(test, null, recipient);
    }

    private ConditionClause(Expression test, Expression consequent, Expression recipient) {
        this.test = Validation.notNull(test);
        Validation.that(consequent == null || recipient == null);
        this.consequent = consequent;
        this.recipient = recipient;
    }

    public boolean hasRecipient() {
        return recipient != null;
    }

    public boolean isTestAlone() {
        return recipient == null && consequent == null;
    }

    public Expression getTest() {
        return test;
    }

    public Expression getRecipient() {
        return recipient;
    }

    public Expression getConsequent() {
        return consequent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConditionClause that = (ConditionClause) o;

        if (!test.equals(that.test)) return false;
        if (recipient != null ? !recipient.equals(that.recipient) : that.recipient != null)
            return false;
        return consequent != null ? consequent.equals(that.consequent) : that.consequent == null;
    }

    @Override
    public int hashCode() {
        int result = test.hashCode();
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (consequent != null ? consequent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return hasRecipient() ? "(" + test + " => " + recipient + ")" : "(" + test + " " + consequent + ")";
    }
}
