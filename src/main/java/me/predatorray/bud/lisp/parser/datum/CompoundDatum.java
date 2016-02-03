package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.parser.BooleanLiteral;
import me.predatorray.bud.lisp.parser.Definition;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.ExpressionVisitor;
import me.predatorray.bud.lisp.parser.Keyword;
import me.predatorray.bud.lisp.parser.LambdaExpression;
import me.predatorray.bud.lisp.parser.ListExpression;
import me.predatorray.bud.lisp.parser.NotApplicableException;
import me.predatorray.bud.lisp.parser.NumberLiteral;
import me.predatorray.bud.lisp.parser.ParserException;
import me.predatorray.bud.lisp.parser.ProcedureCall;
import me.predatorray.bud.lisp.parser.QuoteSpecialForm;
import me.predatorray.bud.lisp.parser.StringLiteral;
import me.predatorray.bud.lisp.parser.Variable;
import me.predatorray.bud.lisp.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CompoundDatum implements Datum {

    private final List<Datum> data;
    private final LeftParenthesis leftParenthesis;

    private final Datum operator;
    private final List<Datum> operands;

    public CompoundDatum(List<Datum> data, LeftParenthesis leftParenthesis) {
        this.data = data;
        this.leftParenthesis = leftParenthesis;

        if (data.isEmpty()) {
            operator = null;
            operands = null;
        } else {
            operator = data.get(0);
            operands = data.subList(1, data.size());
        }
    }

    @Override
    public void accept(DatumVisitor datumVisitor) {
        datumVisitor.visit(this);
    }

    @Override
    public Expression getExpression() {
        if (operator == null) {
            return new ListExpression(Collections.<Expression>emptyList(), leftParenthesis);
        }

        Datum first = data.get(0);
        Expression operatorExpression = first.getExpression();
        CompoundExpressionConstructor constructor = new CompoundExpressionConstructor();
        operatorExpression.accept(constructor);
        return constructor.compoundExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompoundDatum that = (CompoundDatum) o;

        if (!data.equals(that.data)) return false;
        return leftParenthesis.equals(that.leftParenthesis);
    }

    @Override
    public int hashCode() {
        int result = data.hashCode();
        result = 31 * result + leftParenthesis.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "(" + StringUtils.join(data, " ") + ")";
    }

    private class CompoundExpressionConstructor implements ExpressionVisitor {

        private Expression compoundExpression;

        @Override
        public void visit(BooleanLiteral booleanLiteral) {
            throw new NotApplicableException(booleanLiteral);
        }

        @Override
        public void visit(NumberLiteral numberLiteral) {
            throw new NotApplicableException(numberLiteral);
        }

        @Override
        public void visit(StringLiteral stringLiteral) {
            throw new NotApplicableException(stringLiteral);
        }

        @Override
        public void visit(Variable variable) {
            constructProcedureCall(variable);
        }

        @Override
        public void visit(Keyword keyword) {
            assert operands != null;
            int operandSize = operands.size();

            // (quote <datum>)
            if ("quote".equals(keyword.getKeywordName())) {
                if (operandSize != 1) {
                    throw new ParserException("malformed quote expression " + data);
                }
                Datum quoted = operands.get(0);
                compoundExpression = new QuoteSpecialForm(quoted, leftParenthesis);
            }

            // (define <variable> <expression>)
            else if ("define".equals(keyword.getKeywordName())) {
                if (operandSize != 2) {
                    throw new ParserException("malformed definition " + data);
                }
                Datum variableDatum = operands.get(0);
                Expression variable = variableDatum.getExpression();
                if (!(variable instanceof Variable)) {
                    throw new ParserException("a variable is expected here, but is " + variableDatum, variable);
                }
                Expression defExpr = operands.get(1).getExpression();
                compoundExpression = new Definition(((Variable) variable), defExpr, leftParenthesis);
            }

            // (lambda <formals> <body>)
            else if ("lambda".equals(keyword.getKeywordName())) {
                if (operandSize < 2) {
                    throw new ParserException("malformed lambda expression " + data);
                }

                Datum formalsDatum = operands.get(0);
                List<Variable> formals = new LinkedList<Variable>();
                if (formalsDatum instanceof SymbolDatum) {
                    Variable variable = asFormalVariable(formalsDatum);
                    formals.add(variable);
                } else if (formalsDatum instanceof CompoundDatum) {
                    for (Datum datum : ((CompoundDatum) formalsDatum).data) {
                        Variable variable = asFormalVariable(datum);
                        formals.add(variable);
                    }
                } else {
                    throw new ParserException("malformed lambda expression " + data);
                }

                List<Datum> definitionData = operands.subList(1, operandSize - 1);
                if (!definitionData.isEmpty()) {
                    throw new ParserException("definition in a lambda body is currently not supported " + data);
                }
                List<Definition> definitions = Collections.emptyList(); // TODO

                Expression bodyExpression = operands.get(operandSize - 1).getExpression();
                compoundExpression = new LambdaExpression(formals, definitions, bodyExpression, leftParenthesis);
            }

            else {
                throw new ParserException("not an expression " + data);
            }
        }

        private Variable asFormalVariable(Datum datum) {
            if (!(datum instanceof SymbolDatum)) {
                throw new ParserException("malformed lambda expression " + data);
            }
            Expression variable = datum.getExpression();
            if (!(variable instanceof Variable)) {
                throw new ParserException("malformed lambda expression " + data);
            }
            return (Variable) variable;
        }

        @Override
        public void visit(ProcedureCall procedureCall) {
            constructProcedureCall(procedureCall);
        }

        @Override
        public void visit(QuoteSpecialForm quoteSpecialForm) {
            throw new NotApplicableException(quoteSpecialForm);
        }

        @Override
        public void visit(LambdaExpression lambdaExpression) {
            constructProcedureCall(lambdaExpression);
        }

        @Override
        public void visit(Definition definition) {
            throw new NotApplicableException(definition);
        }

        @Override
        public void visit(ListExpression listExpression) {
            throw new NotApplicableException(listExpression);
        }

        @Override
        public void visit(Expression other) {
            throw new ParserException("unknown expression: " + other);
        }

        private void constructProcedureCall(Expression operator) {
            assert operands != null;
            List<Expression> operandExpressions = new ArrayList<Expression>(operands.size());
            for (Datum operand : operands) {
                operandExpressions.add(operand.getExpression());
            }
            compoundExpression = new ProcedureCall(operator, operandExpressions, leftParenthesis);
        }
    }
}
