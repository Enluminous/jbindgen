package generator;

import generator.config.Config;
import generator.generation.*;
import generator.generator.Dependency;
import generator.generator.FuncSymbolGenerator;
import generator.generator.StructGenerator;

import java.util.List;

public class Generator {
    private final List<Generation> availableGen;
    private final Config config;
    private final List<Generation> mustGenerate;

    private final Dependency dependency;

    public Generator(List<Generation> availableGen, Config config, List<Generation> mustGenerate) {
        this.availableGen = availableGen;
        this.config = config;
        this.mustGenerate = mustGenerate;

        dependency = new Dependency();
        for (Generation gen : availableGen) {
            dependency.addGeneration(gen);
        }
    }

    public void generate() {
        for (Generation gen : mustGenerate) {
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
                case CommonGen commonGen -> {

                }
            }
        }
    }
}
