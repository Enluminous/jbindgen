package generator;

import generator.config.Config;
import generator.generation.ConstValue;
import generator.generation.Enum;
import generator.generation.FuncSymbol;
import generator.generation.VarSymbol;

import java.util.List;

public class Generator {
    private final List<VarSymbol> varSymbols;
    private final List<FuncSymbol> funcSymbols;
    private final List<Enum> enumerations;
    private final List<ConstValue> constValues;
    private final Config config;

    public Generator(Config config, List<VarSymbol> varSymbols, List<FuncSymbol> funcSymbols, List<Enum> enumerations, List<ConstValue> constValues) {
        this.config = config;
        this.varSymbols = varSymbols;
        this.funcSymbols = funcSymbols;
        this.enumerations = enumerations;
        this.constValues = constValues;
    }


}
