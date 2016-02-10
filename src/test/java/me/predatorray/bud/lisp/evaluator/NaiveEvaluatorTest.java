package me.predatorray.bud.lisp.evaluator;

import me.predatorray.bud.lisp.buitin.BuiltinsEnvironment;
import me.predatorray.bud.lisp.buitin.EqualPredicate;
import me.predatorray.bud.lisp.lang.*;
import me.predatorray.bud.lisp.parser.*;
import me.predatorray.bud.lisp.test.AbstractBudLispTest;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.same;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class NaiveEvaluatorTest extends AbstractBudLispTest {

    private final NaiveEvaluator sut = new NaiveEvaluator();
    private final Environment EMPTY_ENV = new Environment();

    @Test
    public void testEvaluateString() throws Exception {
        // "hello" ==> "hello"
        String strVal = "hello";
        Expression expr = newStringLiteral(strVal);
        BudObject evaluated = sut.evaluate(expr, EMPTY_ENV);
        BudObject expected = new BudString(strVal);
        assertEquals(expected, evaluated);
    }

    @Test
    public void testEvaluateBoolean1() throws Exception {
        // #t ==> true
        Expression expr = newBooleanLiteral(true);
        BudObject evaluated = sut.evaluate(expr, EMPTY_ENV);
        BudObject expected = BudBoolean.valueOf(true);
        assertEquals(expected, evaluated);
    }

    @Test
    public void testEvaluateBoolean2() throws Exception {
        // #f ==> false
        Expression expr = newBooleanLiteral(false);
        BudObject evaluated = sut.evaluate(expr, EMPTY_ENV);
        BudObject expected = BudBoolean.valueOf(false);
        assertEquals(expected, evaluated);
    }

    @Test
    public void testEvaluateFn1() throws Exception {
        String strVal = "same";
        // (eq "same" "same") ==> true
        Expression expr = newProcedureCall(newVariable("eqv?"), newStringLiteral(strVal), newStringLiteral(strVal));

        Environment initial = new Environment(Collections.<Variable, BudObject>singletonMap(
                newVariable("eqv?"), new EqualPredicate()));
        BudObject evaluated = sut.evaluate(expr, initial);
        BudObject expected = BudBoolean.valueOf(true);
        assertEquals(expected, evaluated);
    }

    @Test
    public void testEvaluateQuote1() throws Exception {
        // (quote a)
        Expression quote = new QuoteSpecialForm(newSymbolDatum("a"), LP);
        BudObject evaluated = sut.evaluate(quote, EMPTY_ENV);
        BudObject expected = new Symbol("a");
        assertEquals(expected, evaluated);
    }

    @Test
    public void testEvaluateQuote2() throws Exception {
        // (quote (a "str"))
        Expression quote = new QuoteSpecialForm(newCompoundDatum(
                newSymbolDatum("a"), newStringDatum("str")), LP);
        BudObject evaluated = sut.evaluate(quote, EMPTY_ENV);
        BudObject expected = new BudList(null, Arrays.asList(new Symbol("a"), new BudString("str")));
        assertEquals(expected, evaluated);
    }

    @Test
    public void testEvaluateLambda1() throws Exception {
        // ((lambda (str1 str2) (eq str1 str2)) "one" "another") ==> false
        Expression body = newProcedureCall(newVariable("eqv?"), newVariable("str1"), newVariable("str2"));
        Expression lambda = new LambdaExpression(Arrays.asList(newVariable("str1"), newVariable("str2")),
                Collections.<Definition>emptyList(), body, LP);
        Expression application = newProcedureCall(lambda, newStringLiteral("one"), newStringLiteral("another"));

        Environment initial = new Environment(Collections.<Variable, BudObject>singletonMap(
                newVariable("eqv?"), new EqualPredicate()));
        BudObject evaluated = sut.evaluate(application, initial);
        BudObject expected = BudBoolean.valueOf(false);
        assertEquals(expected, evaluated);
    }

    @Test
    public void testEvaluateIf1() throws Exception {
        // (if #t "1" "2") ==> "1"
        String first = "1";
        String second = "2";
        Expression ifSpecialForm = new IfSpecialForm(
                newBooleanLiteral(true), newStringLiteral(first), newStringLiteral(second), LP);
        BudObject evaluated = sut.evaluate(ifSpecialForm, EMPTY_ENV);
        assertEquals("(if #t \"1\" \"2\") ==> \"1\"", new BudString(first), evaluated);
    }

    @Test
    public void testEvaluateIf2() throws Exception {
        // (if #f "1" "2") ==> "2"
        String first = "1";
        String second = "2";
        Expression ifSpecialForm = new IfSpecialForm(
                newBooleanLiteral(false), newStringLiteral(first), newStringLiteral(second), LP);
        BudObject evaluated = sut.evaluate(ifSpecialForm, EMPTY_ENV);
        assertEquals("(if #f \"1\" \"2\") ==> \"2\"", new BudString(second), evaluated);
    }

    @Test
    public void testEvaluateIf3() throws Exception {
        // (if '() "1" "2")
        String first = "1";
        String second = "2";
        Expression ifSpecialForm = new IfSpecialForm(
                newQuoteSpecialForm(newCompoundDatum()), newStringLiteral(first), newStringLiteral(second), LP);
        BudObject evaluated = sut.evaluate(ifSpecialForm, EMPTY_ENV);
        assertEquals("(if '() \"1\" \"2\")", new BudString(first), evaluated);
    }

    @Test
    public void testEvaluateAnd1() throws Exception {
        // (and #f #t) ==> #f
        NaiveEvaluator spied = spy(new NaiveEvaluator());
        Expression falseExpression = newBooleanLiteral(false);
        Expression trueExpression = newBooleanLiteral(true);
        Expression andSpecialForm = new AndSpecialForm(Arrays.asList(falseExpression, trueExpression), LP);

        BudObject evaluated = spied.evaluate(andSpecialForm, EMPTY_ENV);

        assertEquals("(and #f #t) ==> #f", BudBoolean.FALSE, evaluated);
        verify(spied).evaluate(same(falseExpression), same(EMPTY_ENV));
        verify(spied, never()).evaluate(same(trueExpression), same(EMPTY_ENV));
    }

    @Test
    public void testEvaluateAnd2() throws Exception {
        // (and '() "false") ==> "false"
        NaiveEvaluator spied = spy(new NaiveEvaluator());
        Expression[] tests = new Expression[] {
                newQuoteSpecialForm(newCompoundDatum()),
                newStringLiteral("false")
        };
        Expression andSpecialForm = new AndSpecialForm(Arrays.asList(tests), LP);

        BudObject evaluated = spied.evaluate(andSpecialForm, EMPTY_ENV);

        assertEquals("(and '() \"false\") ==> \"false\"", new BudString("false"), evaluated);
        verify(spied).evaluate(same(tests[0]), same(EMPTY_ENV));
        verify(spied).evaluate(same(tests[1]), same(EMPTY_ENV));
    }

    @Test
    public void testEvaluateAnd3() throws Exception {
        // (and) ==> #t
        Expression andSpecialForm = new AndSpecialForm(Collections.<Expression>emptyList(), LP);
        BudObject evaluated = sut.evaluate(andSpecialForm, EMPTY_ENV);
        assertEquals("(and) ==> #t", BudBoolean.TRUE, evaluated);
    }

    @Test
    public void testEvaluateOr1() throws Exception {
        // (or #t #f) ==> #t
        NaiveEvaluator spied = spy(new NaiveEvaluator());
        Expression trueExpression = newBooleanLiteral(true);
        Expression falseExpression = newBooleanLiteral(false);
        Expression orSpecialForm = new OrSpecialForm(Arrays.asList(trueExpression, falseExpression), LP);

        BudObject evaluated = spied.evaluate(orSpecialForm, EMPTY_ENV);

        assertEquals("(or #t #f) ==> #t", BudBoolean.TRUE, evaluated);
        verify(spied).evaluate(same(trueExpression), same(EMPTY_ENV));
        verify(spied, never()).evaluate(same(falseExpression), same(EMPTY_ENV));
    }

    @Test
    public void testEvaluateOr2() throws Exception {
        // (or '() #f) ==> #t
        NaiveEvaluator spied = spy(new NaiveEvaluator());
        Expression trueExpression = newBooleanLiteral(true);
        Expression falseExpression = newBooleanLiteral(false);
        Expression orSpecialForm = new OrSpecialForm(Arrays.asList(trueExpression, falseExpression), LP);

        BudObject evaluated = spied.evaluate(orSpecialForm, EMPTY_ENV);

        assertEquals("(or '() #f) ==> #t", BudBoolean.TRUE, evaluated);
        verify(spied).evaluate(same(trueExpression), same(EMPTY_ENV));
        verify(spied, never()).evaluate(same(falseExpression), same(EMPTY_ENV));
    }

    @Test
    public void testEvaluateOr3() throws Exception {
        // (or) ==> #f
        Expression orSpecialForm = new OrSpecialForm(Collections.<Expression>emptyList(), LP);
        BudObject evaluated = sut.evaluate(orSpecialForm, EMPTY_ENV);
        assertEquals("(or) ==> #f", BudBoolean.FALSE, evaluated);
    }

    @Test
    public void testEvaluateCond1() throws Exception {
        // (cond (#t "1") (#f "2")) ==> "1"
        NaiveEvaluator spied = spy(new NaiveEvaluator());
        Expression[] tests = new Expression[] {
                newBooleanLiteral(true),
                newBooleanLiteral(false)
        };
        ConditionClause[] conditionClauses = new ConditionClause[] {
                ConditionClause.newConditionClauseOfConsequentExpression(tests[0], newStringLiteral("1")),
                ConditionClause.newConditionClauseOfConsequentExpression(tests[1], newStringLiteral("2"))
        };
        Expression condSpecialForm = new ConditionSpecialForm(Arrays.asList(conditionClauses), null, LP);

        BudObject evaluated = spied.evaluate(condSpecialForm, EMPTY_ENV);

        assertEquals("(cond (#t \"1\") (#f \"2\")) ==> \"1\"", new BudString("1"), evaluated);
        verify(spied).evaluate(same(tests[0]), same(EMPTY_ENV));
        verify(spied, never()).evaluate(same(tests[1]), same(EMPTY_ENV));
    }

    @Test(expected = EvaluatingException.class)
    public void testEvaluateCond2() throws Exception {
        // (cond (#f "1") (#f "2")) ==> error
        Expression[] tests = new Expression[] {
                newBooleanLiteral(false),
                newBooleanLiteral(false)
        };
        ConditionClause[] conditionClauses = new ConditionClause[] {
                ConditionClause.newConditionClauseOfConsequentExpression(tests[0], newStringLiteral("1")),
                ConditionClause.newConditionClauseOfConsequentExpression(tests[1], newStringLiteral("2"))
        };
        Expression condSpecialForm = new ConditionSpecialForm(Arrays.asList(conditionClauses), null, LP);

        sut.evaluate(condSpecialForm, EMPTY_ENV);
    }

    @Test
    public void testEvaluateCond3() throws Exception {
        // (cond (#f "1") (#f "2") (else "3")) ==> "3"
        NaiveEvaluator spied = spy(new NaiveEvaluator());
        Expression[] tests = new Expression[] {
                newBooleanLiteral(false),
                newBooleanLiteral(false)
        };
        ConditionClause[] conditionClauses = new ConditionClause[] {
                ConditionClause.newConditionClauseOfConsequentExpression(tests[0], newStringLiteral("1")),
                ConditionClause.newConditionClauseOfConsequentExpression(tests[1], newStringLiteral("2")),
        };
        Expression elseExpression = newStringLiteral("3");
        Expression condSpecialForm = new ConditionSpecialForm(Arrays.asList(conditionClauses), elseExpression, LP);

        BudObject evaluated = spied.evaluate(condSpecialForm, EMPTY_ENV);

        assertEquals("(cond (#f \"1\") (#f \"2\") (else \"3\")) ==> \"3\"", new BudString("3"), evaluated);
        verify(spied).evaluate(same(tests[0]), same(EMPTY_ENV));
        verify(spied).evaluate(same(tests[1]), same(EMPTY_ENV));
    }

    @Test
    public void testEvaluateCond4() throws Exception {
        // (cond (1 => -)) ==> -1
        Expression test = newNumberLiteral(1);
        Expression recipient = newVariable("-");
        Expression condSpecialForm = new ConditionSpecialForm(
                Collections.singletonList(ConditionClause.newConditionClauseOfRecipientExpression(test, recipient)),
                null, LP);

        BudObject evaluated = sut.evaluate(condSpecialForm, BuiltinsEnvironment.INSTANCE);

        assertEquals("(cond (1 => -)) ==> -1", new BudNumber(new BigDecimal(-1)), evaluated);
    }
}
