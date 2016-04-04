package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class DivideFunction extends NumericArgumentFunction {

    private static final MathContext DEFAULT_CONTEXT = MathContext.DECIMAL32;

    public DivideFunction() {
        super("/");
    }

    @Override
    protected BudObject applyNumbers(List<BudNumber> numbers) {
        if (numbers.isEmpty()) {
            return new BudNumber(BigDecimal.ONE);
        }
        if (numbers.size() == 1) {
            BigDecimal denominator = numbers.get(0).getValue();
            return new BudNumber(BigDecimal.ONE.divide(denominator, DEFAULT_CONTEXT));
        }

        BigDecimal result = null;
        try {
            for (BudNumber number : numbers) {
                BigDecimal decimal = number.getValue();
                if (result == null) {
                    result = decimal;
                } else {
                    result = result.divide(decimal, DEFAULT_CONTEXT);
                }
            }
            return new BudNumber(result);
        } catch (ArithmeticException e) {
            throw new EvaluatingException("arithmetic error", e);
        }
    }

    @Override
    protected BudType checkArgumentSizeAndInspect(int size) {
        return BudType.NUMBER;
    }
}
