package me.predatorray.bud.lisp.lang;

import java.util.List;

public interface Function extends BudObject {

    BudType inspect(List<BudType> argumentTypes);

    BudObject apply(List<BudObject> arguments);
}
