package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.generation.generator.StructGenerator;
import generator.types.GenerationTypeHolder;
import generator.types.StructType;

public final class Structure extends AbstractGeneration<StructType> {
    public Structure(PackagePath packagePath, GenerationTypeHolder<StructType> type) {
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
