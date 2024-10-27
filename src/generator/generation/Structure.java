package generator.generation;

import generator.Dependency;
import generator.TypePkg;
import generator.PackagePath;
import generator.generation.generator.StructGenerator;
import generator.types.StructType;

public final class Structure extends AbstractGeneration<StructType> {
    public Structure(PackagePath packagePath, StructType type) {
        super(packagePath, type);
    }


    public TypePkg<StructType> getStructType() {
        return typePkg;
    }

    @Override
    public void generate(Dependency dependency) {
        new StructGenerator(this, dependency).generate();
    }
}
