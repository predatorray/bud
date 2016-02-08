package me.predatorray.bud.lisp.test;

import me.predatorray.bud.lisp.lexer.BooleanToken;
import me.predatorray.bud.lisp.lexer.IdentifierToken;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.lexer.NumberToken;
import me.predatorray.bud.lisp.lexer.StringToken;
import me.predatorray.bud.lisp.lexer.TextLocation;
import me.predatorray.bud.lisp.parser.BooleanLiteral;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.NumberLiteral;
import me.predatorray.bud.lisp.parser.ProcedureCall;
import me.predatorray.bud.lisp.parser.QuoteSpecialForm;
import me.predatorray.bud.lisp.parser.StringLiteral;
import me.predatorray.bud.lisp.parser.Variable;
import me.predatorray.bud.lisp.parser.datum.BooleanDatum;
import me.predatorray.bud.lisp.parser.datum.CompoundDatum;
import me.predatorray.bud.lisp.parser.datum.Datum;
import me.predatorray.bud.lisp.parser.datum.StringDatum;
import me.predatorray.bud.lisp.parser.datum.SymbolDatum;

import java.math.BigDecimal;
import java.util.Arrays;

public abstract class AbstractBudLispTest {

    protected final TextLocation DUMMY_LOCATION = new TextLocation(1, 1);
    protected final LeftParenthesis LP = new LeftParenthesis(DUMMY_LOCATION);

    protected Variable newVariable(String variable) {
        return new Variable(new IdentifierToken(variable, DUMMY_LOCATION));
    }

    protected BooleanLiteral newBooleanLiteral(boolean b) {
        return new BooleanLiteral(new BooleanToken(b, DUMMY_LOCATION));
    }

    protected StringLiteral newStringLiteral(String str) {
        return new StringLiteral(new StringToken(str, DUMMY_LOCATION));
    }

    protected NumberLiteral newNumberLiteral(int i) {
        return newNumberLiteral(new BigDecimal(i));
    }

    protected NumberLiteral newNumberLiteral(BigDecimal decimal) {
        return new NumberLiteral(new NumberToken(decimal, DUMMY_LOCATION));
    }

    protected ProcedureCall newProcedureCall(Expression operator, Expression ...operands) {
        return new ProcedureCall(operator, Arrays.asList(operands), LP);
    }

    protected QuoteSpecialForm newQuoteSpecialForm(Datum datum) {
        return new QuoteSpecialForm(datum, LP);
    }

    protected BooleanDatum newBooleanDatum(boolean b) {
        return new BooleanDatum(new BooleanToken(b, DUMMY_LOCATION));
    }

    protected SymbolDatum newSymbolDatum(String symbol) {
        return new SymbolDatum(new IdentifierToken(symbol, DUMMY_LOCATION));
    }

    protected StringDatum newStringDatum(String str) {
        return new StringDatum(new StringToken(str, DUMMY_LOCATION));
    }

    protected CompoundDatum newCompoundDatum(Datum ...data) {
        return new CompoundDatum(Arrays.asList(data), LP);
    }
}
