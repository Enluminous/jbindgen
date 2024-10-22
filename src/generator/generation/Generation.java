package generator.generation;

import generator.TypePkg;
import generator.types.TypeAttr;

import java.util.Set;

public sealed interface Generation<T extends TypeAttr.Type> permits Common, ConstValues, Enumerate, FuncPointer, FuncSymbols, Structure, Value, VarSymbols {
    Set<TypePkg<T>> getImplTypes();

    Set<TypeAttr.Type> getRefTypes();
}
