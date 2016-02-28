package me.predatorray.bud.lisp.evaluator;

import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.ExpressionVisitorAdapter;
import me.predatorray.bud.lisp.parser.ProcedureCall;
import me.predatorray.bud.lisp.util.ConcurrentUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ConcurrentEvaluator implements Evaluator {

    @Override
    public BudObject evaluate(Expression expression, Environment environment) {
        ForkJoinPool pool = new ForkJoinPool();
        TaskConstructor taskConstructor = new TaskConstructor(environment);
        expression.accept(taskConstructor);
        ForkJoinTask<BudObject> submitted = pool.submit(taskConstructor.task);

        try {
            return ConcurrentUtils.getUninterruptibly(submitted);
        } catch (ExecutionException e) {
            throw toRuntimeException(e);
        }
    }

    private static RuntimeException toRuntimeException(ExecutionException e) {
        Throwable cause = e.getCause();
        if (cause instanceof RuntimeException) {
            throw ((RuntimeException) cause);
        } else {
            throw new EvaluatingException("unknown", cause);
        }
    }

    private class SimpleEvaluatingTask extends RecursiveTask<BudObject> {

        private final Expression expression;
        private final Environment environment;

        SimpleEvaluatingTask(Expression expression, Environment environment) {
            this.expression = expression;
            this.environment = environment;
        }

        @Override
        protected BudObject compute() {
            return expression.evaluate(environment, ConcurrentEvaluator.this);
        }
    }

    private class ProcedureCallTask extends RecursiveTask<BudObject> {

        private final ProcedureCall procedureCall;
        private final Environment environment;

        ProcedureCallTask(ProcedureCall procedureCall, Environment environment) {
            this.procedureCall = procedureCall;
            this.environment = environment;
        }

        @Override
        protected BudObject compute() {
            Expression operator = procedureCall.getOperator();
            List<? extends Expression> operands = procedureCall.getOperands();
            int operandSize = operands.size();

            List<RecursiveTask<BudObject>> tasks = new ArrayList<>(operandSize + 1);
            TaskConstructor taskConstructor = new TaskConstructor(environment);
            operator.accept(taskConstructor);
            tasks.add(taskConstructor.task);
            for (Expression operand : operands) {
                operand.accept(taskConstructor);
                tasks.add(taskConstructor.task);
            }

            invokeAll(tasks);
            try {
                BudObject applicative = ConcurrentUtils.getUninterruptibly(tasks.get(0));
                List<BudObject> arguments = new ArrayList<>(operandSize);
                for (int i = 0; i < operandSize; i++) {
                    arguments.add(ConcurrentUtils.getUninterruptibly(tasks.get(i + 1)));
                }
                return ProcedureCall.apply(applicative, arguments);
            } catch (ExecutionException e) {
                throw toRuntimeException(e);
            }
        }
    }

    private class TaskConstructor extends ExpressionVisitorAdapter {

        private final Environment environment;

        RecursiveTask<BudObject> task = null;

        TaskConstructor(Environment environment) {
            this.environment = environment;
        }

        @Override
        public void visit(ProcedureCall procedureCall) {
            task = new ProcedureCallTask(procedureCall, environment);
        }

        @Override
        protected void visitByDefault(final Expression expression) {
            task = new SimpleEvaluatingTask(expression, environment);
        }
    }
}
