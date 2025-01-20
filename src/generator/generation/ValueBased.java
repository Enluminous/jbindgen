package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.ValueBasedGenerator;
import generator.types.GenerationTypeHolder;
import generator.types.ValueBasedType;

public final class ValueBased extends AbstractGeneration<ValueBasedType> {
    public ValueBased(PackagePath packagePath, GenerationTypeHolder<ValueBasedType> type) {
        super(packagePath, type);
    }

    @Override
    public void generate(Dependency dependency) {
        new ValueBasedGenerator(this, dependency).generate();
    }
}
