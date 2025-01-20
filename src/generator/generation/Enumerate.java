package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.EnumGenerator;
import generator.types.EnumType;
import generator.types.GenerationTypeHolder;


public final class Enumerate extends AbstractGeneration<EnumType> {
    public Enumerate(PackagePath packagePath, GenerationTypeHolder<EnumType> type) {
        super(packagePath, type);
    }

    @Override
    public void generate(Dependency dependency) {
        new EnumGenerator(this, dependency).generate();
    }
}
