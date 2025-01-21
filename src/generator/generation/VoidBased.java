package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.RefOnlyGenerator;
import generator.generation.generator.VoidBasedGenerator;
import generator.types.GenerationTypeHolder;
import generator.types.RefOnlyType;
import generator.types.VoidType;

public final class VoidBased extends AbstractGeneration<VoidType> {
    public VoidBased(PackagePath packagePath, GenerationTypeHolder<VoidType> type) {
        super(packagePath, type);
    }

    @Override
    public void generate(Dependency dependency) {
        new VoidBasedGenerator(this).generate();
    }
}
