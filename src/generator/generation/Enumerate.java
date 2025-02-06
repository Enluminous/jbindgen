package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.EnumGenerator;
import generator.types.CommonTypes;
import generator.types.EnumType;
import generator.types.TypeImports;


public final class Enumerate extends AbstractGeneration<EnumType> {
    public Enumerate(PackagePath packagePath, EnumType type) {
        super(packagePath, type);
    }

    @Override
    public TypeImports getDefineImportTypes() {
        return super.getDefineImportTypes().addUseImports(CommonTypes.SpecificTypes.Utils);
    }

    @Override
    public void generate(Dependency dependency) {
        new EnumGenerator(this, dependency).generate();
    }
}
