package me.predatorray.bud.lisp.lang.cont;

import me.predatorray.bud.lisp.lang.BudObject;

public interface Continuation {

    BudObject run();

    Continuation handle(BudObject result);
}
