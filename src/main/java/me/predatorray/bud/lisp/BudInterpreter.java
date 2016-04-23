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
package me.predatorray.bud.lisp;

import me.predatorray.bud.lisp.builtin.BuiltinsEnvironment;
import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.evaluator.NaiveEvaluator;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lexer.Lexer;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.Parser;
import me.predatorray.bud.lisp.util.Validation;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BudInterpreter {

    private final Evaluator evaluator;
    private final Environment initial;

    public BudInterpreter() {
        this(new NaiveEvaluator());
    }

    public BudInterpreter(Evaluator evaluator) {
        this(evaluator, BuiltinsEnvironment.INSTANCE);
    }

    public BudInterpreter(Evaluator evaluator, Environment initial) {
        this.evaluator = Validation.notNull(evaluator);
        this.initial = Validation.notNull(initial);
    }

    public BudObject execute(Path sourceFilePath) throws IOException {
        Validation.notNull(sourceFilePath);
        StringBuilder sourceSequence = new StringBuilder();
        char[] buffer = new char[2048];
        try (BufferedReader reader = Files.newBufferedReader(sourceFilePath, StandardCharsets.UTF_8)) {
            while (true) {
                int read = reader.read(buffer, 0, buffer.length);
                if (read <= 0) {
                    break;
                }
                sourceSequence.append(buffer, 0, read);
            }
        }
        return execute(sourceSequence);
    }

    public BudObject execute(CharSequence source) {
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser();
        List<Expression> expressions = parser.parse(lexer);
        if (expressions.size() == 1) {
            Expression expression = expressions.get(0);
            return evaluator.evaluate(expression, initial);
        } else {
            // TODO same as begin expression
            throw new UnsupportedOperationException();
        }
    }
}
