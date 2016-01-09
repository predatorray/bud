package me.predatorray.bud.lisp.parser;

public interface DatumVisitor {

    void visit(SimpleDatum simpleDatum);

    void visit(CompoundDatum compoundDatum);

    void visit(Datum other);
}
