package me.predatorray.bud.lisp.buitin;

import me.predatorray.bud.lisp.buitin.arithmetic.AddFunction;
import me.predatorray.bud.lisp.buitin.arithmetic.MonoDecreasingPredicate;
import me.predatorray.bud.lisp.buitin.arithmetic.MonoIncreasingPredicate;
import me.predatorray.bud.lisp.buitin.arithmetic.MonoNonDecreasingPredicate;
import me.predatorray.bud.lisp.buitin.arithmetic.MonoNonIncreasingPredicate;
import me.predatorray.bud.lisp.buitin.arithmetic.NumberEqualPredicate;
import me.predatorray.bud.lisp.buitin.arithmetic.SubtractFunction;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;

import java.util.HashMap;
import java.util.Map;

public class BuiltinsEnvironment {

    public static final Environment INSTANCE;
    static {
        Map<String, BudObject> initial = new HashMap<>();

        initial.put("eq?", new EqualPredicate());
        initial.put("equals?", new EqualPredicate());
        initial.put("boolean?", new BooleanPredicate());
        initial.put("string?", new StringPredicate());
        initial.put("number?", new NumberPredicate());
        initial.put("list?", new ListPredicate());
        initial.put("function?", new FunctionPredicate());
        initial.put("null?", new NullPredicate());

        initial.put("+", new AddFunction());
        initial.put("-", new SubtractFunction());
        initial.put("=", new NumberEqualPredicate());
        initial.put("<", new MonoIncreasingPredicate());
        initial.put(">", new MonoDecreasingPredicate());
        initial.put(">=", new MonoNonIncreasingPredicate());
        initial.put("<=", new MonoNonDecreasingPredicate());

        initial.put("null-environment", new NullEnvironmentFunction());
        initial.put("builtins-environment", new BuiltinsEnvironmentFunction());

        INSTANCE = Environment.toEnvironment(initial);
    }
}
