package generator.symbols;

import generator.types.TypeAttr;

public class VarSymbol {
    private final TypeAttr.NormalType normalType;

    public VarSymbol(TypeAttr.NormalType normalType) {
        this.normalType = normalType;
    }
}
