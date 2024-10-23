package generator.generation;

import generator.TypePkg;
import generator.types.TypeAttr;

import java.util.Set;

public sealed interface Generation<T extends TypeAttr.Type> permits AbstractGeneration, Common, ConstValues, FuncSymbols, VarSymbols {
    Set<TypePkg<T>> getImplTypes();

    Set<TypeAttr.Type> getRefTypes();
}
