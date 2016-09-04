package me.predatorray.bud.lisp.builtin;

import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.Function;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFunctionTest {

    private final Function function;

    public AbstractFunctionTest(Function function) {
        this.function = function;
    }

    protected final void assertApplyCorrectly(String message, BudObject expectedResult, BudObject ...arguments) {
        BudObject actual = exercise(arguments);
        Assert.assertEquals(message, expectedResult, actual);
    }

    protected final BudObject exercise(BudObject ...arguments) {
        List<BudObject> argumentList = Arrays.asList(arguments);
        List<BudType> typeList = new ArrayList<>(argumentList.size());
        for (BudObject object : argumentList) {
            typeList.add(object.getType());
        }
        function.inspect(typeList);
        return function.apply(argumentList);
    }
}
