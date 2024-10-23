package generator.generation;

import generator.PackagePath;
import generator.types.EnumType;


public final class Enumerate extends AbstractGeneration<EnumType> {
    public Enumerate(PackagePath packagePath, EnumType type) {
        super(packagePath, type);
    }
}
