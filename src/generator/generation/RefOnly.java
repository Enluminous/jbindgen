package generator.generation;

import generator.PackagePath;
import generator.types.RefOnlyType;

public final class RefOnly extends AbstractGeneration<RefOnlyType> {
    public RefOnly(PackagePath packagePath, RefOnlyType type) {
        super(packagePath, type);
    }
}
