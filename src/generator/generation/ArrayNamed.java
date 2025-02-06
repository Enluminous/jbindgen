package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.ArrayNamedGenerator;
import generator.types.ArrayTypeNamed;
import generator.types.CommonTypes;
import generator.types.TypeImports;

public final class ArrayNamed extends AbstractGeneration<ArrayTypeNamed> {
    public ArrayNamed(PackagePath packagePath, ArrayTypeNamed type) {
        super(packagePath, type);
    }

    @Override
    public TypeImports getDefineImportTypes() {
        return super.getDefineImportTypes()
                .addUseImports(CommonTypes.FFMTypes.MEMORY_SEGMENT)
                .addUseImports(CommonTypes.SpecificTypes.ArrayOp)
                .addUseImports(CommonTypes.SpecificTypes.MemoryUtils);
    }

    @Override
    public void generate(Dependency dependency) {
        new ArrayNamedGenerator(this, dependency).generate();
    }
}
