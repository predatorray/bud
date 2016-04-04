package me.predatorray.bud.lisp.buitin;

import me.predatorray.bud.lisp.buitin.arithmetic.*;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;

import java.util.HashMap;
import java.util.Map;

public class BuiltinsEnvironment {

    public static final Environment INSTANCE;
    static {
        Map<String, BudObject> initial = new HashMap<>();

        NamedFunction[] builtInFunctions = new NamedFunction[] {
                new EqPredicate(),
                new EqualPredicate(),
                new BooleanPredicate(),
                new StringPredicate(),
                new NumberPredicate(),
                new ListPredicate(),
                new FunctionPredicate(),
                new NullPredicate(),

                new CarFunction(),
                new CdrFunction(),
                new ConsFunction(),

                new AddFunction(),
                new SubtractFunction(),
                new MultiplyFunction(),
                new DivideFunction(),
                new AbsFunction(),
                new NumberEqualPredicate(),
                new MonoIncreasingPredicate(),
                new MonoDecreasingPredicate(),
                new MonoNonIncreasingPredicate(),
                new MonoNonDecreasingPredicate(),
                new IntegerPredicate(),
                new MaxFunction(),
                new MinFunction(),
                new PositivePredicate(),
                new NegativePredicate(),
                new ZeroPredicate(),
                new OddPredicate(),
                new EvenPredicate(),

                new NullEnvironmentFunction(),
                new BuiltinsEnvironmentFunction(),
                new ConvertListElementTypeFunction()
        };
        for (NamedFunction builtInFunction : builtInFunctions) {
            builtInFunction.register(initial);
        }

        INSTANCE = Environment.toEnvironment(initial, "builtins");
    }
}
