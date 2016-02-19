package me.predatorray.bud.lisp;

import me.predatorray.bud.lisp.buitin.BuiltinsEnvironment;
import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.evaluator.NaiveEvaluator;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lexer.Lexer;
import me.predatorray.bud.lisp.lexer.LexerException;
import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.Parser;
import me.predatorray.bud.lisp.parser.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class BudRepl {

    private final Evaluator evaluator;
    private final Environment initial;

    private final boolean interactive;

    public BudRepl() {
        this.evaluator = new NaiveEvaluator();
        this.initial = BuiltinsEnvironment.INSTANCE;
        this.interactive = true;
    }

    private class ReplParserCallback implements Parser.ParserCallback {

        private final PrintWriter stdPrintWriter;

        private boolean evaluated = true;

        ReplParserCallback(PrintWriter stdPrintWriter) {
            this.stdPrintWriter = stdPrintWriter;
        }

        @Override
        public void expressionReady(Expression expression) {
            evaluated = true;
            BudObject evaluated = evaluator.evaluate(expression, initial);
            if (evaluated != null) {
                stdPrintWriter.println(evaluated.toString());
                stdPrintWriter.flush();
            }
        }

        public boolean hasAnyExpressionBeenEvaluated() {
            boolean consumed = evaluated;
            evaluated = false;
            return consumed;
        }
    }

    public void execute(Reader reader, final Writer writer, Writer errorWriter) throws IOException {
        BufferedReader lineReader = new BufferedReader(reader);
        final PrintWriter stdPrintWriter = toPrintWriter(writer);
        final PrintWriter errPrintWriter = toPrintWriter(errorWriter);

        ReplParserCallback replParserCallback = new ReplParserCallback(stdPrintWriter);
        Parser parser = new Parser(replParserCallback);

        boolean exceptionOccurred = false;
        while (true) {
            try {
                if (interactive) {
                    if (replParserCallback.hasAnyExpressionBeenEvaluated() || exceptionOccurred) {
                        stdPrintWriter.print("bud> ");
                    } else {
                        stdPrintWriter.print("   > ");
                    }
                    stdPrintWriter.flush();
                }

                String line = lineReader.readLine();
                if (line == null) {
                    break;
                }

                // TODO if ended with backslash
                Lexer lexer = new Lexer(line);
                Iterator<Token> tokenIterator = lexer.iterator();
                parser.feed(tokenIterator);
                exceptionOccurred = false;
            } catch (LexerException | ParserException | EvaluatingException e) {
                PrintWriter target = (interactive ? stdPrintWriter : errPrintWriter);
                target.println(e.getLocalizedMessage());
                target.flush();
                exceptionOccurred = true;
            }
        }
    }

    private PrintWriter toPrintWriter(Writer writer) {
        return writer instanceof PrintWriter ? ((PrintWriter) writer) : new PrintWriter(writer);
    }

    public static void main(String... args) throws Exception {
        BudRepl repl = new BudRepl();
        Charset replEncoding = StandardCharsets.UTF_8;
        try {
            repl.execute(new InputStreamReader(System.in, replEncoding),
                    new OutputStreamWriter(System.out, replEncoding),
                    new OutputStreamWriter(System.err, replEncoding));
        } catch (Exception unknownException) {
            unknownException.printStackTrace();
        }
    }
}
