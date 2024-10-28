package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.RefOnlyGenerator;
import generator.types.RefOnlyType;

public final class RefOnly extends AbstractGeneration<RefOnlyType> {
    public RefOnly(PackagePath packagePath, RefOnlyType type) {
        super(packagePath, type);
    }

    @Override
    public void generate(Dependency dependency) {
        new RefOnlyGenerator(this, dependency).generate();
    }
}
