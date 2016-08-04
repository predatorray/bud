package me.predatorray.bud.lisp.lang.cont;

import me.predatorray.bud.lisp.lang.BudObject;
import me.predatorray.bud.lisp.lang.Continuous;

@Deprecated
public class ContinuousCont implements Continuation {

    private final Continuous continuous;
    private Continuous next;

    public ContinuousCont(Continuous continuous) {
        this.continuous = continuous;
    }

    @Override
    public BudObject run() {
        next = continuous.getSuccessor();
        return continuous.getResult();
    }

    @Override
    public Continuation handle(BudObject ignored) {
        if (next != null) {
            return new ContinuousCont(next);
        }
        return null;
    }
}
