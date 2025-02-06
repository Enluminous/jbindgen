package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.StructGenerator;
import generator.types.CommonTypes;
import generator.types.StructType;
import generator.types.TypeAttr;
import generator.types.TypeImports;
import generator.types.operations.CommonOperation;

public final class Structure extends AbstractGeneration<StructType> {
    public Structure(PackagePath packagePath, StructType type) {
        super(packagePath, type);
    }

    @Override
    public TypeImports getDefineImportTypes() {
        TypeImports imports = super.getDefineImportTypes().addUseImports(CommonTypes.FFMTypes.MEMORY_SEGMENT)
                .addUseImports(CommonTypes.FFMTypes.SEGMENT_ALLOCATOR)
                .addUseImports(CommonTypes.SpecificTypes.MemoryUtils);
        for (StructType.Member member : getTypePkg().type().getMembers()) {
            CommonOperation commonOperation = ((TypeAttr.OperationType) member.type()).getOperation().getCommonOperation();
            commonOperation.getUpperType().typeRefers().forEach(imports::addUseImports);
            commonOperation.makeOperation().typeRefers().forEach(imports::addUseImports);
        }
        return imports;
    }

    @Override
    public void generate(Dependency dependency) {
        new StructGenerator(this, dependency).generate();
    }
}
