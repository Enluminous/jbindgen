package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.ValueBasedGenerator;
import generator.types.Holder;
import generator.types.TypeAttr;
import generator.types.ValueBasedType;

import java.util.HashSet;
import java.util.Set;

public final class ValueBased extends AbstractGeneration<ValueBasedType> {
    public ValueBased(PackagePath packagePath, Holder<ValueBasedType> type) {
        super(packagePath, type);
    }

    @Override
    public void generate(Dependency dependency) {
        new ValueBasedGenerator(this, dependency).generate();
    }
}
