package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.parser.AndSpecialForm;
import me.predatorray.bud.lisp.parser.ConditionClause;
import me.predatorray.bud.lisp.parser.ConditionSpecialForm;
import me.predatorray.bud.lisp.parser.Definition;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.IfSpecialForm;
import me.predatorray.bud.lisp.parser.LambdaExpression;
import me.predatorray.bud.lisp.parser.NotApplicableException;
import me.predatorray.bud.lisp.parser.OrSpecialForm;
import me.predatorray.bud.lisp.parser.ParserException;
import me.predatorray.bud.lisp.parser.ProcedureCall;
import me.predatorray.bud.lisp.parser.Variable;
import me.predatorray.bud.lisp.test.AbstractBudLispTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class CompoundDatumTest extends AbstractBudLispTest {

    @Test(expected = ParserException.class)
    public void testGetExpressionOfEmptyList() {
        // () ==> error
        CompoundDatum datum = newCompoundDatum();
        datum.getExpression();
    }

    @Test
    public void testGetExpressionOfProcedureCall1() {
        // (<variable>)
        // (+ 1)
        CompoundDatum datum = newCompoundDatum(newSymbolDatum("+"), newNumberDatum(1));
        Expression expression = datum.getExpression();
        ProcedureCall expected = newProcedureCall(newVariable("+"), newNumberLiteral(1));
        assertEquals("(+ 1)", expected, expression);
    }

    @Test
    public void testGetExpressionOfProcedureCall2() {
        // (<variable> <operand>*)
        // (foobar)
        CompoundDatum datum = newCompoundDatum(newSymbolDatum("foobar"));
        Expression expression = datum.getExpression();
        ProcedureCall expected = newProcedureCall(newVariable("foobar"));
        assertEquals("(foobar)", expected, expression);
    }

    @Test
    public void testGetExpressionOfProcedureCall3() {
        // (<procedure-call> <operand>*)
        // ((foobar) "str")
        CompoundDatum datum = newCompoundDatum(newCompoundDatum(newSymbolDatum("foobar")), newStringDatum("str"));
        Expression expression = datum.getExpression();
        ProcedureCall expected = newProcedureCall(newProcedureCall(newVariable("foobar")), newStringLiteral("str"));
        assertEquals("((foobar) \"str\")", expected, expression);
    }

    @Test
    public void testGetExpressionOfProcedureCall4() {
        // (<lambda-special-form> <operand>*)
        // ((lambda () +) 1 2 3)
        CompoundDatum lambdaDatum = newCompoundDatum(newSymbolDatum("lambda"), newCompoundDatum(), newSymbolDatum("+"));
        CompoundDatum datum = newCompoundDatum(lambdaDatum, newNumberDatum(1), newNumberDatum(2), newNumberDatum(3));

        LambdaExpression lambda = new LambdaExpression(Collections.<Variable>emptyList(),
                Collections.<Definition>emptyList(), newVariable("+"), LP);
        ProcedureCall expected = newProcedureCall(lambda, newNumberLiteral(1), newNumberLiteral(2),
                newNumberLiteral(3));
        assertEquals("((lambda () +) 1 2 3)", expected, datum.getExpression());
    }

    @Test
    public void testGetExpressionOfProcedureCall5() {
        // (<if-special-form> <operand>*)
        // ((if #t + -) 1)
        CompoundDatum ifDatum = newCompoundDatum(newSymbolDatum("if"), newBooleanDatum(true),
                newSymbolDatum("+"), newSymbolDatum("-"));
        CompoundDatum datum = newCompoundDatum(ifDatum, newNumberDatum(1));

        IfSpecialForm ifSpecialForm = new IfSpecialForm(newBooleanLiteral(true), newVariable("+"), newVariable("-"),
                LP);
        ProcedureCall expected = newProcedureCall(ifSpecialForm, newNumberLiteral(1));
        assertEquals("((if #t + -) 1)", expected, datum.getExpression());
    }

    @Test
    public void testGetExpressionOfProcedureCall6() {
        // (<and-special-form> <operand>*)
        // ((and #t +) 1)
        CompoundDatum andDatum = newCompoundDatum(newSymbolDatum("and"), newBooleanDatum(true), newSymbolDatum("+"));
        CompoundDatum datum = newCompoundDatum(andDatum, newNumberDatum(1));

        AndSpecialForm andSpecialForm = new AndSpecialForm(Arrays.<Expression>asList(
                newBooleanLiteral(true), newVariable("+")), LP);
        ProcedureCall expected = newProcedureCall(andSpecialForm, newNumberLiteral(1));
        assertEquals("((and #t +) 1)", expected, datum.getExpression());
    }

    @Test
    public void testGetExpressionOfProcedureCall7() {
        // (<or-special-form> <operand>*)
        // ((or #f +) 1)
        CompoundDatum orDatum = newCompoundDatum(newSymbolDatum("or"), newBooleanDatum(false), newSymbolDatum("+"));
        CompoundDatum datum = newCompoundDatum(orDatum, newNumberDatum(1));

        OrSpecialForm orSpecialForm = new OrSpecialForm(Arrays.<Expression>asList(
                newBooleanLiteral(false), newVariable("+")), LP);
        ProcedureCall expected = newProcedureCall(orSpecialForm, newNumberLiteral(1));
        assertEquals("((or #f +) 1)", expected, datum.getExpression());
    }

    @Test
    public void testGetExpressionOfProcedureCall8() {
        // (<cond-special-form> <operand>*)
        // ((cond (#f -) (#t +)) 0)
        CompoundDatum condDatum = newCompoundDatum(newSymbolDatum("cond"),
                newCompoundDatum(newBooleanDatum(false), newSymbolDatum("-")),
                newCompoundDatum(newBooleanDatum(true), newSymbolDatum("+")));
        CompoundDatum datum = newCompoundDatum(condDatum, newNumberDatum(0));

        ConditionClause[] clauses = new ConditionClause[] {
                ConditionClause.newConditionClauseOfConsequentExpression(newBooleanLiteral(false), newVariable("-")),
                ConditionClause.newConditionClauseOfConsequentExpression(newBooleanLiteral(true), newVariable("+"))
        };
        ConditionSpecialForm conditionSpecialForm = new ConditionSpecialForm(Arrays.asList(clauses), null, LP);
        ProcedureCall expected = newProcedureCall(conditionSpecialForm, newNumberLiteral(0));
        assertEquals("((cond (#f -) (#t +)) 0)", expected, datum.getExpression());
    }

    @Test(expected = NotApplicableException.class)
    public void testGetExpressionOfIllegalForm1() {
        // (<quote-special-form> <operand>*)
        // ((quote #t)) ==> error
        CompoundDatum quoteDatum = newCompoundDatum(newSymbolDatum("quote"), newBooleanDatum(true));
        CompoundDatum datum = newCompoundDatum(quoteDatum);

        datum.getExpression();
    }

    @Test(expected = NotApplicableException.class)
    public void testGetExpressionOfIllegalForm2() {
        // (<boolean> <operand>*)
        // (#t) ==> error
        CompoundDatum datum = newCompoundDatum(newBooleanDatum(true));
        datum.getExpression();
    }

    @Test(expected = NotApplicableException.class)
    public void testGetExpressionOfIllegalForm3() {
        // (<number> <operand>*)
        // (1) ==> error
        CompoundDatum datum = newCompoundDatum(newNumberDatum(1));
        datum.getExpression();
    }

    @Test(expected = NotApplicableException.class)
    public void testGetExpressionOfIllegalForm4() {
        // (<string> <operand>*)
        // ("+") ==> error
        CompoundDatum datum = newCompoundDatum(newStringDatum("+"));
        datum.getExpression();
    }
}
