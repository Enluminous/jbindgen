package generator;

import generator.config.Config;
import generator.generation.*;
import generator.generator.Dependency;
import generator.generator.FuncSymbolGenerator;
import generator.generator.StructGenerator;
import generator.types.TypeAttr;

import java.util.HashMap;
import java.util.List;

public class Generator {
    private final List<AbstractGeneration> availableGen;
    private final Config config;
    private final List<AbstractGeneration> mustGenerate;

    private final Dependency dependency;

    public Generator(List<AbstractGeneration> availableGen, Config config, List<AbstractGeneration> mustGenerate) {
        this.availableGen = availableGen;
        this.config = config;
        this.mustGenerate = mustGenerate;
        // todo add common generations
        dependency = new Dependency();
        for (AbstractGeneration gen : availableGen) {
            dependency.addGeneration(gen);
        }
    }

    public void generate() {
        for (AbstractGeneration gen : mustGenerate) {
            switch (gen) {
                case ConstValues constValues -> {
                }
                case EnumGen enumGen -> {
                }
                case FuncPointer funcPointer -> {
                }
                case FuncSymbols funcSymbols -> new FuncSymbolGenerator(funcSymbols, dependency).generate();
                case StructGen structGen -> new StructGenerator(structGen, dependency).generate();
                case ValueGen valueGen -> {
                }
                case VarSymbols varSymbols -> {
                }
            }
        }
    }
}
