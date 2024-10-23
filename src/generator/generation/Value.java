package generator.generation;

import generator.config.PackagePath;
import generator.types.ValueBasedType;

public final class Value extends AbstractGeneration<ValueBasedType> {
    public Value(PackagePath packagePath, ValueBasedType type) {
        super(packagePath, type);
    }
}
