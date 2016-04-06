package me.predatorray.bud.lisp.lang;

import java.util.List;

public interface TailCallFunction extends Function {

    BudFuture applyAndGetBudFuture(List<BudObject> arguments);
}
