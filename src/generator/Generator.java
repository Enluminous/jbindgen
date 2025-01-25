package generator;

import generator.generation.Generation;
import generator.types.Holder;
import generator.types.TypeAttr;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static utils.CommonUtils.Assert;

public class Generator {
    public interface GenerationProvider {
        Generation<? extends TypeAttr.GenerationType> queryGeneration(Holder<?> type);
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
        HashSet<Holder<?>> generated = new HashSet<>();
        do {
            HashSet<Holder<?>> reference = new HashSet<>();
            for (Generation<?> gen : generations) {
                generated.addAll(gen.getImplTypes().stream().map(TypePkg::typeHolder).toList());
                for (TypeAttr.ReferenceType referType : gen.getDefineReferTypes()) {
                    referType.toGenerationTypes().ifPresent(reference::add);
                }
            }
            reference.removeAll(generated);
            Set<Generation<?>> newGen = new HashSet<>();
            while (!reference.isEmpty()) {
                Holder<?> type = reference.iterator().next();
                Generation<? extends TypeAttr.GenerationType> generation = provider.queryGeneration(type);
                Assert(generation != null, "missing generation: " + type);
                List<? extends Holder<?>> impl = generation.getImplTypes().stream().map(TypePkg::typeHolder).toList();
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
