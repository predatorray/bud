package me.predatorray.bud.lisp.lang;

public final class CompletedBudFuture implements BudFuture {

    private final BudObject result;

    public CompletedBudFuture(BudObject result) {
        this.result = result;
    }

    @Override
    public BudObject getResult() {
        return result;
    }

    @Override
    public BudFuture getTailCall() {
        return null;
    }
}
