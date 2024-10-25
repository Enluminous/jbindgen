package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.types.ValueBasedType;

public final class Value extends AbstractGeneration<ValueBasedType> {
    public Value(PackagePath packagePath, ValueBasedType type) {
        super(packagePath, type);
    }

    @Override
    public void generate(Dependency dependency) {
        System.err.println("todo: generate this");
    }
}
