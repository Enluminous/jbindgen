package generator;

import generator.constants.ConstValue;
import generator.constants.Enum;
import generator.symbols.FuncSymbol;
import generator.symbols.VarSymbol;

import java.util.List;

public class Generator {
    private final List<VarSymbol> varSymbols;
    private final List<FuncSymbol> funcSymbols;
    private final List<Enum> enumerations;
    private final List<ConstValue> constValues;

    public Generator(List<VarSymbol> varSymbols, List<FuncSymbol> funcSymbols, List<Enum> enumerations, List<ConstValue> constValues) {
        this.varSymbols = varSymbols;
        this.funcSymbols = funcSymbols;
        this.enumerations = enumerations;
        this.constValues = constValues;
    }


}
