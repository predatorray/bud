package me.predatorray.bud.lisp.lang;

import me.predatorray.bud.lisp.evaluator.EvaluatingException;
import me.predatorray.bud.lisp.parser.Definition;
import me.predatorray.bud.lisp.parser.Expression;
import me.predatorray.bud.lisp.parser.LambdaExpression;
import me.predatorray.bud.lisp.parser.Variable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LambdaFunction implements Function {

    private final FunctionType thisType = new FunctionType(this);
    private final List<Variable> formals;
    private final List<Definition> definitions;
    private final Expression body;

    private final LambdaExpression lambdaExpression;
    private final Environment lexicalEnv;
    private final Variable self;

    public LambdaFunction(LambdaExpression lambdaExpression, Environment lexicalEnv, Variable self) {
        this.lambdaExpression = lambdaExpression;
        this.lexicalEnv = lexicalEnv;
        this.self = self;
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

        Map<Variable, BudObject> argumentBindings = new HashMap<>(actualSize);
        for (int i = 0; i < actualSize; i++) {
            Variable formal = formals.get(i);
            BudObject actual = arguments.get(i);
            argumentBindings.put(formal, actual);
        }

        MutableEnvironment envDefined = new MutableEnvironment(lexicalEnv);
        if (self != null) {
            envDefined.bind(self, this);
        }
        for (Definition definition : definitions) {
            definition.bind(envDefined);
        }
        Environment enclosing = new Environment(argumentBindings, envDefined.toEnvironment());
        return body.evaluate(enclosing);
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
        if (!lexicalEnv.equals(that.lexicalEnv)) return false;
        return self != null ? self.equals(that.self) : that.self == null;
    }

    @Override
    public int hashCode() {
        int result = lambdaExpression.hashCode();
        result = 31 * result + lexicalEnv.hashCode();
        result = 31 * result + (self != null ? self.hashCode() : 0);
        return result;
    }
}
