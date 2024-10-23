package generator.generation;

import generator.config.PackagePath;
import generator.types.PointerType;

public final class StandardPointer extends AbstractGeneration<PointerType> {
    public StandardPointer(PackagePath packagePath, PointerType type) {
        super(packagePath, type);
    }
}
