package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.VoidBasedGenerator;
import generator.types.CommonTypes;
import generator.types.TypeImports;
import generator.types.VoidType;

public final class VoidBased extends AbstractGeneration<VoidType> {
    public VoidBased(PackagePath packagePath, VoidType type) {
        super(packagePath, type);
    }

    @Override
    public TypeImports getDefineImportTypes() {
        return super.getDefineImportTypes().addUseImports(CommonTypes.BasicOperations.Info);
    }

    @Override
    public void generate(Dependency dependency) {
        if (typePkg.type().realVoid()) // skip this type
            return;
        new VoidBasedGenerator(this, dependency).generate();
    }
}
