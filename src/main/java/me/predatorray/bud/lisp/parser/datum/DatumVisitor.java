package me.predatorray.bud.lisp.parser.datum;

public interface DatumVisitor {

    void visit(BooleanDatum booleanDatum);

    void visit(NumberDatum numberDatum);

    void visit(StringDatum stringDatum);

    void visit(SymbolDatum symbolDatum);

    void visit(CompoundDatum compoundDatum);
}
