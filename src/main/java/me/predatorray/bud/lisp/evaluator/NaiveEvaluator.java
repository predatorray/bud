package me.predatorray.bud.lisp.evaluator;

import me.predatorray.bud.lisp.lang.BudBoolean;
import me.predatorray.bud.lisp.lang.BudList;
import me.predatorray.bud.lisp.lang.BudNumber;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.BudString;
import me.predatorray.bud.lisp.lang.BudType;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lang.Function;
import me.predatorray.bud.lisp.lang.LambdaFunction;
import me.predatorray.bud.lisp.lang.Symbol;
import me.predatorray.bud.lisp.parser.AndSpecialForm;
import me.predatorray.bud.lisp.parser.BooleanLiteral;
import me.predatorray.bud.lisp.parser.Definition;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.ExpressionVisitor;
import me.predatorray.bud.lisp.parser.IfSpecialForm;
import me.predatorray.bud.lisp.parser.Keyword;
import me.predatorray.bud.lisp.parser.LambdaExpression;
import me.predatorray.bud.lisp.parser.NumberLiteral;
import me.predatorray.bud.lisp.parser.OrSpecialForm;
import me.predatorray.bud.lisp.parser.ProcedureCall;
import me.predatorray.bud.lisp.parser.QuoteSpecialForm;
import me.predatorray.bud.lisp.parser.StringLiteral;
import me.predatorray.bud.lisp.parser.Variable;
import me.predatorray.bud.lisp.parser.datum.BooleanDatum;
import me.predatorray.bud.lisp.parser.datum.CompoundDatum;
import me.predatorray.bud.lisp.parser.datum.Datum;
import me.predatorray.bud.lisp.parser.datum.DatumVisitor;
import me.predatorray.bud.lisp.parser.datum.NumberDatum;
import me.predatorray.bud.lisp.parser.datum.StringDatum;
import me.predatorray.bud.lisp.parser.datum.SymbolDatum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NaiveEvaluator implements Evaluator {

    @Override
    public BudObject evaluate(Expression expression, Environment environment) {
        ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator(environment);
        expression.accept(expressionEvaluator);
        return expressionEvaluator.evaluated;
    }

    class ExpressionEvaluator implements ExpressionVisitor {

        private final Environment environment;
        private BudObject evaluated;

        public ExpressionEvaluator(Environment environment) {
            this.environment = environment;
        }

        @Override
        public void visit(BooleanLiteral booleanLiteral) {
            evaluated = BudBoolean.valueOf(booleanLiteral.getValue());
        }

        @Override
        public void visit(NumberLiteral numberLiteral) {
            BigDecimal decimal = numberLiteral.getValue();
            evaluated = new BudNumber(decimal);
        }

        @Override
        public void visit(StringLiteral stringLiteral) {
            evaluated = new BudString(stringLiteral.getValue());
        }

        @Override
        public void visit(Variable variable) {
            BudObject bound = environment.lookup(variable);
            if (bound == null) {
                throw new EvaluatingException("unbound variable: " + variable);
            }
            evaluated = bound;
        }

        @Override
        public void visit(Keyword keyword) {
            throw new EvaluatingException("cannot evaluate a keyword " + keyword);
        }

        @Override
        public void visit(ProcedureCall procedureCall) {
            Expression operator = procedureCall.getOperator();
            BudObject applicable = evaluate(operator, environment);
            if (!BudType.Category.FUNCTION.equals(applicable.getType().getCategory())) {
                throw new EvaluatingException("not applicable", operator);
            }
            Function function = (Function) applicable;

            List<? extends Expression> operands = procedureCall.getOperands();

            List<BudType> argTypes = new ArrayList<>(operands.size());
            List<BudObject> arguments = new ArrayList<>(operands.size());
            for (Expression operand : operands) {
                BudObject arg = evaluate(operand, environment);
                argTypes.add(arg.getType());
                arguments.add(arg);
            }
            function.inspect(argTypes);
            evaluated = function.apply(arguments);
        }

        @Override
        public void visit(QuoteSpecialForm quoteSpecialForm) {
            Datum quotedDatum = quoteSpecialForm.getQuotedDatum();
            DatumObjectConstructor constructor = new DatumObjectConstructor();
            quotedDatum.accept(constructor);
            evaluated = constructor.datumObject;
        }

        @Override
        public void visit(IfSpecialForm ifSpecialForm) {
            Expression test = ifSpecialForm.getTest();
            BudObject tested = evaluate(test, environment);
            if (!BudBoolean.FALSE.equals(tested)) { // any object not #f is treated as true
                evaluated = evaluate(ifSpecialForm.getConsequent(), environment);
            } else {
                evaluated = evaluate(ifSpecialForm.getAlternate(), environment);
            }
        }

        @Override
        public void visit(AndSpecialForm andSpecialForm) {
            List<Expression> tests = andSpecialForm.getTests();
            BudObject eachTested = BudBoolean.TRUE;
            for (Expression test : tests) {
                eachTested = evaluate(test, environment);
                if (BudBoolean.FALSE.equals(eachTested)) {
                    evaluated = eachTested;
                    return;
                }
            }
            evaluated = eachTested;
        }

        @Override
        public void visit(OrSpecialForm orSpecialForm) {
            List<Expression> tests = orSpecialForm.getTests();
            BudObject eachTested = BudBoolean.FALSE;
            for (Expression test : tests) {
                eachTested = evaluate(test, environment);
                if (!BudBoolean.FALSE.equals(eachTested)) {
                    evaluated = eachTested;
                    return;
                }
            }
            evaluated = eachTested;
        }

        @Override
        public void visit(LambdaExpression lambdaExpression) {
            evaluated = new LambdaFunction(lambdaExpression, environment, NaiveEvaluator.this);
        }

        @Override
        public void visit(Definition definition) {
            // TODO return new Environment
        }

        @Override
        public void visit(Expression other) {
            // TODO unknown
        }
    }

    class DatumObjectConstructor implements DatumVisitor {

        private BudObject datumObject;

        @Override
        public void visit(BooleanDatum booleanDatum) {
            datumObject = BudBoolean.valueOf(booleanDatum.getValue());
        }

        @Override
        public void visit(NumberDatum numberDatum) {
            datumObject = new BudNumber(numberDatum.getValue());
        }

        @Override
        public void visit(StringDatum stringDatum) {
            datumObject = new BudString(stringDatum.getValue());
        }

        @Override
        public void visit(SymbolDatum symbolDatum) {
            datumObject = new Symbol(symbolDatum.getName());
        }

        @Override
        public void visit(CompoundDatum compoundDatum) {
            List<Datum> data = compoundDatum.getData();
            List<BudObject> objects = new ArrayList<>(data.size());
            for (Datum datum : data) {
                DatumObjectConstructor constructor = new DatumObjectConstructor();
                datum.accept(constructor);
                objects.add(constructor.datumObject);
            }
            datumObject = new BudList(null, objects);
        }
    }
}
