package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.types.RefOnlyType;

public final class RefOnly extends AbstractGeneration<RefOnlyType> {
    public RefOnly(PackagePath packagePath, RefOnlyType type) {
        super(packagePath, type);
    }

    @Override
    public void generate(Dependency dependency) {
        System.err.println("todo: generate this");
    }
}
