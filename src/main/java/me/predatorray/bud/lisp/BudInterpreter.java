package me.predatorray.bud.lisp;

import me.predatorray.bud.lisp.buitin.BuiltinsEnvironment;
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
