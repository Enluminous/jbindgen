package generator.generation;

import generator.config.PackagePath;
import generator.types.ValueBasedType;

public final class ValueGen extends AbstractGeneration {
    private final ValueBasedType type;

    public ValueGen(PackagePath packagePath, ValueBasedType type) {
        super(packagePath.end(type.getTypeName()));
        this.type = type;
    }
}
