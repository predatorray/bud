package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.parser.*;
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
            throw new ParserException("() is not an expression");
        }

        Datum first = data.get(0);
        Expression operatorExpression = first.getExpression();
        CompoundExpressionConstructor constructor = new CompoundExpressionConstructor();
        operatorExpression.accept(constructor);
        return constructor.compoundExpression;
    }

    public List<Datum> getData() {
        return data;
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

            // (lambda <formals> <body>)
            else if ("lambda".equals(keyword.getKeywordName())) {
                if (operandSize < 2) {
                    throw new ParserException("malformed lambda expression " + CompoundDatum.this);
                }

                Datum formalsDatum = operands.get(0);
                List<Variable> formals = new LinkedList<>();
                if (formalsDatum instanceof SymbolDatum) {
                    Variable variable = asFormalVariable(formalsDatum);
                    formals.add(variable);
                } else if (formalsDatum instanceof CompoundDatum) {
                    for (Datum datum : ((CompoundDatum) formalsDatum).data) {
                        Variable variable = asFormalVariable(datum);
                        formals.add(variable);
                    }
                } else {
                    throw new ParserException("malformed lambda expression " + CompoundDatum.this);
                }

                List<Datum> definitionData = operands.subList(1, operandSize - 1);
                if (!definitionData.isEmpty()) {
                    throw new ParserException("definition in a lambda body is currently not supported " +
                            CompoundDatum.this);
                }
                List<Definition> definitions = Collections.emptyList(); // TODO

                Expression bodyExpression = operands.get(operandSize - 1).getExpression();
                compoundExpression = new LambdaExpression(formals, definitions, bodyExpression, leftParenthesis);
            }

            // (if <test> <consequent> <alternate>)
            else if ("if".equals(keyword.getKeywordName())) {
                if (operandSize != 3) {
                    throw new ParserException("malformed if expression " + CompoundDatum.this);
                }

                Datum test = operands.get(0);
                Datum consequent = operands.get(1);
                Datum alternate = operands.get(2);
                compoundExpression = new IfSpecialForm(test.getExpression(), consequent.getExpression(),
                        alternate.getExpression(), leftParenthesis);
            }

            // (and <test>*)
            else if ("and".equals(keyword.getKeywordName())) {
                compoundExpression = new AndSpecialForm(toExpressions(operands), leftParenthesis);
            }

            // (or <test>*)
            else if ("or".equals(keyword.getKeywordName())) {
                List<Expression> tests = new ArrayList<>(operandSize);
                for (Datum operand : operands) {
                    tests.add(operand.getExpression());
                }
                compoundExpression = new OrSpecialForm(tests, leftParenthesis);
            }

            // (cond <clause>+) | (cond <clause>* <else-clause>)
            //
            // clause := (<test> <consequent>)
            //         | (<test> => (recipient))
            //
            // else-clause := (else <expression>)
            else if ("cond".equals(keyword.getKeywordName())) {
                if (operandSize < 1) {
                    throw new ParserException("cond special form must have at least one clause " + CompoundDatum.this);
                }

                List<ConditionClause> clauses = new ArrayList<>(operandSize);
                Expression elseExpression = null;
                for (int i = 0; i < operandSize; i++) {
                    Datum operand = operands.get(i);
                    if (!(operand instanceof CompoundDatum)) {
                        throw new ParserException("malformed cond special form " + CompoundDatum.this);
                    }

                    List<Datum> data = ((CompoundDatum) operand).getData();
                    int clauseElements = data.size();
                    if (clauseElements > 3 || clauseElements < 1) {
                        throw new ParserException("malformed clause of cond special form " + CompoundDatum.this);
                    }

                    List<Expression> condOperandExpressions = toExpressions(data);

                    Expression first = condOperandExpressions.get(0);
                    Expression second = condOperandExpressions.get(1);
                    if (i == operandSize - 1
                            && first instanceof Keyword
                            && "else".equals(((Keyword) first).getKeywordName())) { // else-clause
                        elseExpression = second;
                        break;
                    }

                    ConditionClause clause;
                    switch (clauseElements) {
                        case 1:
                            clause = ConditionClause.newConditionClauseOfTestAlone(condOperandExpressions.get(0));
                            break;
                        case 2:
                            clause = ConditionClause.newConditionClauseOfConsequentExpression(
                                    first, condOperandExpressions.get(1));
                            break;
                        case 3:
                        default:
                            if (!(second instanceof Keyword)) {
                                throw new ParserException("malformed clause of cond special form " +
                                        CompoundDatum.this);
                            }
                            if (!"=>".equals(((Keyword) second).getKeywordName())) {
                                throw new ParserException("malformed clause of cond special form " +
                                        CompoundDatum.this);
                            }
                            clause = ConditionClause.newConditionClauseOfRecipientExpression(
                                    first, condOperandExpressions.get(2));
                            break;
                    }
                    clauses.add(clause);
                }
                compoundExpression = new ConditionSpecialForm(clauses, elseExpression, leftParenthesis);
            }

            else if (keyword.isExpressionKeyword()) {
                throw new ParserException(keyword + " is not supported");
            } else {
                throw new ParserException("not an expression " + CompoundDatum.this);
            }
        }

        private Variable asFormalVariable(Datum datum) {
            if (!(datum instanceof SymbolDatum)) {
                throw new ParserException("malformed lambda expression " + CompoundDatum.this);
            }
            Expression variable = datum.getExpression();
            if (!(variable instanceof Variable)) {
                throw new ParserException("malformed lambda expression " + CompoundDatum.this);
            }
            return (Variable) variable;
        }

        private List<Expression> toExpressions(List<Datum> data) {
            List<Expression> expressions = new ArrayList<>(data.size());
            for (Datum datum : data) {
                expressions.add(datum.getExpression());
            }
            return expressions;
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
        public void visit(IfSpecialForm ifSpecialForm) {
            constructProcedureCall(ifSpecialForm);
        }

        @Override
        public void visit(AndSpecialForm andSpecialForm) {
            constructProcedureCall(andSpecialForm);
        }

        @Override
        public void visit(OrSpecialForm orSpecialForm) {
            constructProcedureCall(orSpecialForm);
        }

        @Override
        public void visit(ConditionSpecialForm conditionSpecialForm) {
            constructProcedureCall(conditionSpecialForm);
        }

        @Override
        public void visit(LambdaExpression lambdaExpression) {
            constructProcedureCall(lambdaExpression);
        }

        @Override
        public void visit(Expression other) {
            throw new ParserException("unknown expression: " + other);
        }

        private void constructProcedureCall(Expression operator) {
            assert operands != null;
            List<Expression> operandExpressions = new ArrayList<>(operands.size());
            for (Datum operand : operands) {
                operandExpressions.add(operand.getExpression());
            }
            compoundExpression = new ProcedureCall(operator, operandExpressions, leftParenthesis);
        }
    }
}
