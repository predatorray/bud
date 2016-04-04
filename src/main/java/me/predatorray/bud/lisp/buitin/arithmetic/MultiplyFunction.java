package me.predatorray.bud.lisp.buitin.arithmetic;

import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.math.BigDecimal;
import java.util.List;

public class MultiplyFunction extends NumericArgumentFunction {

    public MultiplyFunction() {
        super("*");
    }

    @Override
    protected BudObject applyNumbers(List<BudNumber> numbers) {
        if (numbers.isEmpty()) {
            return new BudNumber(BigDecimal.ONE);
        }

        BigDecimal result = null;
        for (BudNumber argument : numbers) {
            BigDecimal number = argument.getValue();
            if (result == null) {
                result = number;
            } else {
                result = result.multiply(number);
            }
        }
        return new BudNumber(result);
    }

    @Override
    protected BudType checkArgumentSizeAndInspect(int size) {
        return BudType.NUMBER;
    }
}
