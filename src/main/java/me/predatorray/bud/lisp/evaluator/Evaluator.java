package me.predatorray.bud.lisp.evaluator;

import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.parser.Expression;

public interface Evaluator {

    BudObject evaluate(Expression expression, Environment environment);
}
