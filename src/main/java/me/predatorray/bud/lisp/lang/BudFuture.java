package me.predatorray.bud.lisp.lang;

public interface BudFuture {

    BudObject getResult();

    BudFuture getTailCall();
}
