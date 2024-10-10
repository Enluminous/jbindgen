package generator.generation;

import generator.TypePkg;
import generator.types.TypeAttr;

import java.util.Set;

public sealed interface Generation permits CommonGen, ConstValues, EnumGen, FuncPointer, FuncSymbols, StructGen, ValueGen, VarSymbols {
    Set<TypePkg<?>> getImplTypes();

    Set<TypeAttr.Type> getRefTypes();
}
