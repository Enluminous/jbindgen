package generator;

import generator.config.Config;
import generator.generation.*;
import generator.generator.Dependency;
import generator.generator.FuncSymbolGenerator;
import generator.generator.StructGenerator;

import java.util.List;

public class Generator {
    private final List<Generation> availableGen;
    private final List<Generation> mustGenerate;

    private final Dependency dependency;

    /**
     * generate java files
     *
     * @param availableGen generate is available, but only generate when ref this
     * @param mustGenerate must generate this, when missing symbols, will throw
     */
    public Generator(List<Generation> availableGen, List<Generation> mustGenerate) {
        this.availableGen = availableGen;
        this.mustGenerate = mustGenerate;
        dependency = new Dependency().addGeneration(availableGen).addGeneration(mustGenerate);
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
