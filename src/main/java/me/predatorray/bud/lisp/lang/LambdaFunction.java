package me.predatorray.bud.lisp.lang;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.parser.Definition;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.LambdaExpression;
import me.predatorray.bud.lisp.parser.Variable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LambdaFunction implements Function {

    private final FunctionType thisType;
    private final List<Variable> formals;
    private final List<Definition> definitions;
    private final Expression body;

    private final LambdaExpression lambdaExpression;
    private final Evaluator evaluator;
    private final Environment lexicalEnv;

    public LambdaFunction(LambdaExpression lambdaExpression, Environment lexicalEnv, Evaluator evaluator) {
        this.lambdaExpression = lambdaExpression;
        this.lexicalEnv = lexicalEnv;
        this.evaluator = evaluator;
        thisType = new FunctionType(this);
        this.formals = lambdaExpression.getFormals();
        this.definitions = lambdaExpression.getDefinitions();
        this.body = lambdaExpression.getBodyExpression();
    }

    @Override
    public BudType inspect(List<BudType> argumentTypes) {
        return null; // TODO
    }

    @Override
    public BudObject apply(List<BudObject> arguments) {
        int requires = formals.size();
        int actualSize = arguments.size();
        if (requires != actualSize) {
            String msg = "requires " + requires + " arguments, but " + actualSize + " actually";
            throw new EvaluatingException(msg, lambdaExpression);
        }

        Map<Variable, BudObject> argumentBindings = new HashMap<Variable, BudObject>(actualSize);
        for (int i = 0; i < actualSize; i++) {
            Variable formal = formals.get(i);
            BudObject actual = arguments.get(i);
            argumentBindings.put(formal, actual);
        }

        MutableEnvironment envDefined = new MutableEnvironment(lexicalEnv);
        for (Definition definition : definitions) {
            Environment env = envDefined.toEnvironment();
            Variable variable = definition.getVariable();
            BudObject defined = evaluator.evaluate(definition.getExpression(), env);
            envDefined.bind(variable, defined);
        }
        Environment enclosing = new Environment(argumentBindings, envDefined.toEnvironment());
        return evaluator.evaluate(body, enclosing);
    }

    @Override
    public BudType getType() {
        return thisType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LambdaFunction that = (LambdaFunction) o;

        if (!lambdaExpression.equals(that.lambdaExpression)) return false;
        return lexicalEnv.equals(that.lexicalEnv);
    }

    @Override
    public int hashCode() {
        int result = lambdaExpression.hashCode();
        result = 31 * result + lexicalEnv.hashCode();
        return result;
    }
}
