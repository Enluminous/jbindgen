package generator.generation;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.types.StructType;

public final class Structure extends AbstractGeneration<StructType> {
    public Structure(PackagePath packagePath, StructType type) {
        super(packagePath, type);
    }


    public TypePkg<StructType> getStructType() {
        return typePkg;
    }
}
