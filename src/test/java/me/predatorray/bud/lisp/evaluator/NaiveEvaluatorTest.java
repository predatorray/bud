package me.predatorray.bud.lisp.evaluator;

import me.predatorray.bud.lisp.buitin.EqualPredicate;
import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudList;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudString;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lang.Symbol;
import me.predatorray.bud.lisp.parser.Definition;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.LambdaExpression;
import me.predatorray.bud.lisp.parser.QuoteSpecialForm;
import me.predatorray.bud.lisp.parser.Variable;
import me.predatorray.bud.lisp.test.AbstractBudLispTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

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
}
