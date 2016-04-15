package me.predatorray.bud.lisp.builtin.arithmetic;

import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;

import java.math.BigDecimal;
import java.util.List;

public class SubtractFunction extends NumericArgumentFunction {

    public SubtractFunction() {
        super("-");
    }

    @Override
    protected BudObject applyNumbers(List<BudNumber> numbers) {
        if (numbers.isEmpty()) {
            return new BudNumber(BigDecimal.ZERO);
        }

        if (numbers.size() == 1) {
            BudNumber number = numbers.get(0);
            return new BudNumber(number.getValue().negate());
        }

        BigDecimal result = null;
        for (BudNumber argument : numbers) {
            BigDecimal number = argument.getValue();
            if (result == null) {
                result = number;
            } else {
                result = result.subtract(number);
            }
        }
        return new BudNumber(result);
    }

    @Override
    protected BudType checkArgumentSizeAndInspect(int size) {
        return BudType.NUMBER;
    }
}
