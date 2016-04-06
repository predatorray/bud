package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.evaluator.Evaluator;
import me.predatorray.bud.lisp.lang.BudFuture;
import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Environment;
import me.predatorray.bud.lisp.lexer.TextLocation;

public interface Expression {

    TextLocation getLocation();

    void accept(ExpressionVisitor expressionVisitor);

    BudObject evaluate(Environment environment, Evaluator evaluator);

    BudFuture evaluateAndGetBudFuture(Environment environment, Evaluator evaluator);
}
