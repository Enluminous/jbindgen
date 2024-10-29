package generator;

import generator.generation.Generation;
import generator.types.TypeAttr;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static utils.CommonUtils.Assert;

public class Generator {
    public interface GenerationProvider {
        Generation<? extends TypeAttr.Type> queryGeneration(TypeAttr.Type type);
    }

    private final List<Generation<?>> mustGenerate;

    private final Dependency dependency;
    private final GenerationProvider provider;

    /**
     * generate java files
     *
     * @param provider     provide other generations
     * @param mustGenerate must generate this, when missing symbols, will throw
     */
    public Generator(List<Generation<?>> mustGenerate, GenerationProvider provider) {
        this.mustGenerate = mustGenerate;
        dependency = new Dependency()
                .addType(mustGenerate.stream().map(Generation::getImplTypes).flatMap(Set::stream).toList());
        this.provider = provider;
    }

    public void generate() {
        Set<Generation<?>> generations = new HashSet<>(mustGenerate);
        HashSet<TypeAttr.Type> generated = new HashSet<>();
        do {
            LinkedHashSet<TypeAttr.Type> reference = new LinkedHashSet<>();
            for (Generation<?> gen : generations) {
                generated.addAll(gen.getImplTypes().stream().map(TypePkg::type).toList());
                for (TypeAttr.ReferenceType referType : gen.getDefineReferTypes()) {
                    reference.addAll(referType.toGenerationTypes());
                }
            }
            reference.removeAll(generated);
            Set<Generation<?>> newGen = new HashSet<>();
            while (!reference.isEmpty()) {
                TypeAttr.Type type = reference.getFirst();
                Generation<? extends TypeAttr.Type> generation = provider.queryGeneration(type);
                Assert(generation != null, "generation is null: " + type);
                List<? extends TypeAttr.Type> impl = generation.getImplTypes().stream().map(TypePkg::type).toList();
                Assert(impl.contains(type), "missing type generation:" + type);
                impl.forEach(reference::remove);
                newGen.add(generation);
                dependency.addType(generation.getImplTypes());
            }
            for (Generation<?> generation : generations) {
                generation.generate(dependency);
            }
            generations.clear();
            generations.addAll(newGen);
        } while (!generations.isEmpty());
    }
}
