package generator.generation;

import generator.config.PackagePath;
import generator.types.VoidType;

public class Void extends AbstractGeneration<VoidType> {
    public Void(PackagePath packagePath, VoidType type) {
        super(packagePath, type);
    }
}
