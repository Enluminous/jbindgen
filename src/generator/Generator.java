package generator;

import generator.generation.*;
import generator.generator.CommonGenerator;
import generator.generator.FuncSymbolGenerator;
import generator.generator.StructGenerator;
import generator.types.TypeAttr;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static utils.CommonUtils.Assert;

public class Generator {
    private final List<Generation<?>> mustGenerate;

    private final Dependency dependency;
    private final HashMap<TypeAttr.Type, Generation<?>> allGenerations = new HashMap<>();

    /**
     * generate java files
     *
     * @param availableGen generate is available, but only generate when ref this
     * @param mustGenerate must generate this, when missing symbols, will throw
     */
    public Generator(List<Generation<?>> availableGen, List<Generation<?>> mustGenerate) {
        this.mustGenerate = mustGenerate;
        dependency = new Dependency().addGeneration(availableGen).addGeneration(mustGenerate);
        Consumer<Generation<?>> fillGeneration = generation -> generation.getImplTypes()
                .forEach(typePkg -> allGenerations.put(typePkg.type(), generation));
        availableGen.forEach(fillGeneration);
        mustGenerate.forEach(fillGeneration);
    }

    public void generate() {
        Set<Generation<?>> generations = new HashSet<>(mustGenerate);
        HashSet<TypeAttr.Type> generated = new HashSet<>();
        do {
            HashSet<TypeAttr.Type> reference = new HashSet<>();
            for (Generation<?> gen : generations) {
                generated.addAll(gen.getImplTypes().stream().map(TypePkg::type).toList());
                reference.addAll(gen.getRefTypes());
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
                    default -> throw new IllegalStateException("Unexpected value: " + gen);
                }
            }
            generations.clear();
            reference.removeAll(generated);
            for (TypeAttr.Type type : reference) {
                Assert(allGenerations.containsKey(type), "missing type generation:" + type);
                generations.add(allGenerations.get(type));
            }
        } while (!generations.isEmpty());
    }
}
