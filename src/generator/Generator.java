package generator;

import generator.generation.*;
import generator.generator.CommonGenerator;
import generator.generator.Dependency;
import generator.generator.FuncSymbolGenerator;
import generator.generator.StructGenerator;

import java.util.List;

public class Generator {
    private final List<Generation<?>> availableGen;
    private final List<Generation<?>> mustGenerate;

    private final Dependency dependency;

    /**
     * generate java files
     *
     * @param availableGen generate is available, but only generate when ref this
     * @param mustGenerate must generate this, when missing symbols, will throw
     */
    public Generator(List<Generation<?>> availableGen, List<Generation<?>> mustGenerate) {
        this.availableGen = availableGen;
        this.mustGenerate = mustGenerate;
        dependency = new Dependency().addGeneration(availableGen).addGeneration(mustGenerate);
    }

    public void generate() {
        for (Generation<?> gen : mustGenerate) {
            switch (gen) {
                case ConstValues constValues -> {
                }
                case Enumerate enumerate -> {
                }
                case FuncPointer funcPointer -> {
                }
                case FuncSymbols funcSymbols -> new FuncSymbolGenerator(funcSymbols, dependency).generate();
                case Structure structure -> new StructGenerator(structure, dependency).generate();
                case Value value -> {
                }
                case VarSymbols varSymbols -> {
                }
                case Common common -> new CommonGenerator(common, dependency).generate();
            }
        }
    }
}
