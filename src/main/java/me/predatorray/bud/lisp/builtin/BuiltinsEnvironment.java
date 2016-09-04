/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Wenhao Ji
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package me.predatorray.bud.lisp.builtin;

import me.predatorray.bud.lisp.builtin.arithmetic.AbsFunction;
import me.predatorray.bud.lisp.builtin.arithmetic.AddFunction;
import me.predatorray.bud.lisp.builtin.arithmetic.DivideFunction;
import me.predatorray.bud.lisp.builtin.arithmetic.EvenPredicate;
import me.predatorray.bud.lisp.builtin.arithmetic.IntegerPredicate;
import me.predatorray.bud.lisp.builtin.arithmetic.MaxFunction;
import me.predatorray.bud.lisp.builtin.arithmetic.MinFunction;
import me.predatorray.bud.lisp.builtin.arithmetic.MonoDecreasingPredicate;
import me.predatorray.bud.lisp.builtin.arithmetic.MonoIncreasingPredicate;
import me.predatorray.bud.lisp.builtin.arithmetic.MonoNonDecreasingPredicate;
import me.predatorray.bud.lisp.builtin.arithmetic.MonoNonIncreasingPredicate;
import me.predatorray.bud.lisp.builtin.arithmetic.MultiplyFunction;
import me.predatorray.bud.lisp.builtin.arithmetic.NegativePredicate;
import me.predatorray.bud.lisp.builtin.arithmetic.NumberEqualPredicate;
import me.predatorray.bud.lisp.builtin.arithmetic.OddPredicate;
import me.predatorray.bud.lisp.builtin.arithmetic.PositivePredicate;
import me.predatorray.bud.lisp.builtin.arithmetic.SubtractFunction;
import me.predatorray.bud.lisp.builtin.arithmetic.ZeroPredicate;
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
