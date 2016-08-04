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
import me.predatorray.bud.lisp.evaluator.ContinuationEvaluator;
import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lexer.Lexer;
import me.predatorray.bud.lisp.lexer.LexerException;
import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.Parser;
import me.predatorray.bud.lisp.parser.ParserException;

import java.io.BufferedReader;
import java.io.Console;
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
        this(true);
    }

    public BudRepl(boolean interactive) {
        this.evaluator = new ContinuationEvaluator();
        this.initial = BuiltinsEnvironment.INSTANCE;
        this.interactive = interactive;
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
        final PrintWriter out = toPrintWriter(writer);
        final PrintWriter err = toPrintWriter(errorWriter);

        ReplParserCallback replParserCallback = new ReplParserCallback(out);
        Parser parser = new Parser(replParserCallback);

        boolean exceptionOccurred = false;
        while (true) {
            if (exceptionOccurred) {
                parser = new Parser(replParserCallback);
            }
            try {
                if (interactive) {
                    if (replParserCallback.hasAnyExpressionBeenEvaluated() || exceptionOccurred) {
                        out.print("bud> ");
                    } else {
                        out.print("   > ");
                    }
                    out.flush();
                }

                String line = lineReader.readLine();
                if (line == null) {
                    break;
                }

                Lexer lexer = new Lexer(line);
                Iterator<Token> tokenIterator = lexer.iterator();
                parser.feed(tokenIterator);
                exceptionOccurred = false;
            } catch (LexerException | ParserException | EvaluatingException e) {
                PrintWriter target = (interactive ? out : err);
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
        Console console = System.console();
        BudRepl repl = new BudRepl(console != null);
        Charset replEncoding = StandardCharsets.UTF_8;

        Reader in = console == null ? new InputStreamReader(System.in, replEncoding) : console.reader();
        Writer out = console == null ? new OutputStreamWriter(System.out, replEncoding) : console.writer();
        Writer err = new OutputStreamWriter(System.err, replEncoding);
        try {
            repl.execute(in, out, err);
        } catch (Exception unknownException) {
            unknownException.printStackTrace();
        }
    }
}
