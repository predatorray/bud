package me.predatorray.bud.lisp.lang.cont;

import me.predatorray.bud.lisp.lang.BudObject;

public class Termination implements Continuation {

    private final BudObject result;

    public Termination(BudObject result) {
        this.result = result;
    }

    @Override
    public BudObject run() {
        return result;
    }

    @Override
    public Continuation handle(BudObject result) {
        return null;
    }
}
