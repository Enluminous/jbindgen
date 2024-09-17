package generator;

import generator.config.Config;
import generator.generation.*;
import generator.types.TypeAttr;

import java.util.HashMap;
import java.util.List;

public class Generator {
    private final List<VarSymbols> varSymbols;
    private final List<FuncSymbols> funcSymbols;
    private final List<EnumGen> enumerations;
    private final List<ConstValues> constValues;
    private final Config config;

    public Generator(Config config, List<VarSymbols> varSymbols, List<FuncSymbols> funcSymbols, List<EnumGen> enumerations, List<ConstValues> constValues) {
        this.config = config;
        this.varSymbols = varSymbols;
        this.funcSymbols = funcSymbols;
        this.enumerations = enumerations;
        this.constValues = constValues;
    }


}
