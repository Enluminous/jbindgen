package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.StructGenerator;
import generator.types.CommonTypes;
import generator.types.StructType;
import generator.types.TypeAttr;
import generator.types.TypeImports;
import generator.types.operations.OperationAttr;

public final class Structure extends AbstractGeneration<StructType> {
    public Structure(PackagePath packagePath, StructType type) {
        super(packagePath, type);
    }

    @Override
    public TypeImports getDefineImportTypes() {
        TypeImports imports = super.getDefineImportTypes().addUseImports(CommonTypes.FFMTypes.MEMORY_SEGMENT)
                .addUseImports(CommonTypes.FFMTypes.SEGMENT_ALLOCATOR)
                .addUseImports(CommonTypes.SpecificTypes.MemoryUtils)
                .addUseImports(CommonTypes.ValueInterface.I64I)
                .addUseImports(CommonTypes.FFMTypes.MEMORY_LAYOUT)
                .addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT);
        for (StructType.Member member : getTypePkg().type().getMembers()) {
            OperationAttr.Operation operation = ((TypeAttr.OperationType) member.type()).getOperation();
            imports.addImport(operation.getMemoryOperation().setter("", 0, "").imports());
            imports.addImport(operation.getMemoryOperation().getter("", 0).imports());
            imports.addImport(typePkg.type().getMemoryLayout().getTypeImports());
        }
        return imports;
    }

    @Override
    public void generate(Dependency dependency) {
        new StructGenerator(this, dependency).generate();
    }
}
