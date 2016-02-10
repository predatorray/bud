package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.util.StringUtils;
import me.predatorray.bud.lisp.util.Validation;

import java.util.Arrays;
import java.util.List;

abstract class CompoundExpression extends TokenLocatedExpression {

    private final String operatorName;
    private final List<?> operands;

    public CompoundExpression(LeftParenthesis leading, String operatorName, List<?> operands) {
        super(leading);
        this.operatorName = Validation.notNull(operatorName);
        this.operands = Validation.notNull(operands);
    }

    public CompoundExpression(LeftParenthesis leading, String operatorName, Expression ...operandExpressions) {
        this(leading, operatorName, Arrays.asList(operandExpressions));
    }

    @Override
    public String toString() {
        return "(" + operatorName + " " + StringUtils.join(operands, " ") + ")";
    }
}
