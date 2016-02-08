package me.predatorray.bud.lisp.buitin;

import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.evaluator.NaiveEvaluator;
import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.parser.Definition;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.LambdaExpression;
import me.predatorray.bud.lisp.test.AbstractBudLispTest;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class BuiltinsEnvironmentTest extends AbstractBudLispTest {

    private final Environment initial = BuiltinsEnvironment.INSTANCE;
    private final Evaluator evaluator = new NaiveEvaluator();

    private void assertEvaluatesCorrectly(String message, BudObject expected, Expression input) {
        BudObject actual = evaluator.evaluate(input, initial);
        assertEquals(message, expected, actual);
    }

    @Test
    public void testBooleanPredicate1() {
        // (boolean? #f) ===> true
        Expression booleanPredicateApplication = newProcedureCall(newVariable("boolean?"), newBooleanLiteral(false));
        BudObject expected = BudBoolean.TRUE;
        assertEvaluatesCorrectly("(boolean? #f) ===> true", expected, booleanPredicateApplication);
    }

    @Test
    public void testBooleanPredicate2() {
        // (boolean? '(#f)) ==> false
        Expression booleanPredicateApplication = newProcedureCall(newVariable("boolean?"),
                newQuoteSpecialForm(newCompoundDatum(newBooleanDatum(false))));
        BudObject expected = BudBoolean.FALSE;
        assertEvaluatesCorrectly("(boolean? '(#f)) ==> false", expected, booleanPredicateApplication);
    }

    @Test
    public void testStringPredicate1() {
        // (string? "") ==> true
        Expression stringPredicateApplication = newProcedureCall(newVariable("string?"), newStringLiteral(""));
        BudObject expected = BudBoolean.TRUE;
        assertEvaluatesCorrectly("(string? \"\") ==> true", expected, stringPredicateApplication);
    }

    @Test
    public void testStringPredicate2() {
        // (string? 'foobar) ==> false
        Expression stringPredicateApplication = newProcedureCall(newVariable("string?"),
                newQuoteSpecialForm(newSymbolDatum("foobar")));
        BudObject expected = BudBoolean.FALSE;
        assertEvaluatesCorrectly("(string? 'foobar)", expected, stringPredicateApplication);
    }

    @Test
    public void testNumberPredicate1() {
        // (number? 0) ==> true
        Expression numberPredicateApplication = newProcedureCall(newVariable("number?"), newNumberLiteral(0));
        BudObject expected = BudBoolean.TRUE;
        assertEvaluatesCorrectly("(number? 0) ==> true", expected, numberPredicateApplication);
    }

    @Test
    public void testNumberPredicate2() {
        // (number? "123") ==> false
        Expression numberPredicateApplication = newProcedureCall(newVariable("number?"), newStringLiteral("123"));
        BudObject expected = BudBoolean.FALSE;
        assertEvaluatesCorrectly("(number? 0) ==> false", expected, numberPredicateApplication);
    }

    @Test
    public void testListPredicate1() {
        // (list? '()) ==> true
        Expression listPredicateApplication = newProcedureCall(newVariable("list?"),
                newQuoteSpecialForm(newCompoundDatum()));
        BudObject expected = BudBoolean.TRUE;
        assertEvaluatesCorrectly("(list? '()) ==> true", expected, listPredicateApplication);
    }

    @Test
    public void testListPredicate2() {
        // (list? (+ 1 2)) ==> false
        Expression listPredicateApplication = newProcedureCall(newVariable("list?"),
                newProcedureCall(newVariable("+"), newNumberLiteral(1), newNumberLiteral(2)));
        BudObject expected = BudBoolean.FALSE;
        assertEvaluatesCorrectly("(list? (+ 1 2)) ==> false", expected, listPredicateApplication);
    }

    @Test
    public void testFunctionPredicate1() {
        // (function? +) ==> true
        Expression functionPredicateApplication = newProcedureCall(newVariable("function?"), newVariable("+"));
        BudObject expected = BudBoolean.TRUE;
        assertEvaluatesCorrectly("(function? +) ==> true", expected, functionPredicateApplication);
    }

    @Test
    public void testFunctionPredicate2() {
        // (function? (lambda (a) a)) ==> true
        LambdaExpression lambda = new LambdaExpression(Collections.singletonList(newVariable("a")),
                Collections.<Definition>emptyList(), newVariable("a"), LP);
        Expression functionPredicateApplication = newProcedureCall(newVariable("function?"), lambda);
        BudObject expected = BudBoolean.TRUE;
        assertEvaluatesCorrectly("(function? (lambda (a) a)) ==> true", expected, functionPredicateApplication);
    }

    @Test
    public void testFunctionPredicate3() {
        // (function? 'foobar) ==> true
        Expression functionPredicateApplication = newProcedureCall(newVariable("function?"),
                newQuoteSpecialForm(newSymbolDatum("foobar")));
        BudObject expected = BudBoolean.FALSE;
        assertEvaluatesCorrectly("(function? 'foobar) ==> true", expected, functionPredicateApplication);
    }

    @Test
    public void testNullPredicate1() {
        // (null? '()) ==> true
        Expression nullPredicateApplication = newProcedureCall(newVariable("null?"),
                newQuoteSpecialForm(newCompoundDatum()));
        BudObject expected = BudBoolean.TRUE;
        assertEvaluatesCorrectly("(null? '()) ==> true", expected, nullPredicateApplication);
    }

    @Test
    public void testNullPredicate2() {
        // (null? #f) ==> false
        Expression nullPredicateApplication = newProcedureCall(newVariable("null?"), newBooleanLiteral(false));
        BudObject expected = BudBoolean.FALSE;
        assertEvaluatesCorrectly("(null? #f) ==> false", expected, nullPredicateApplication);
    }
}
