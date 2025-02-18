package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.FuncProtocolGenerator;
import generator.types.*;
import generator.types.operations.CommonOperation;
import generator.types.operations.OperationAttr;

/**
 * used to generate function pointer, normally used in callback ptr
 */
public final class FuncPointer extends AbstractGeneration<FunctionPtrType> {
    public FuncPointer(PackagePath packagePath, FunctionPtrType type) {
        super(packagePath, type);
    }

    @Override
    public TypeImports getDefineImportTypes() {
        TypeImports imports = super.getDefineImportTypes();
        for (MemoryLayouts memoryLayout : typePkg.type().getMemoryLayouts()) {
            imports.addUseImports(memoryLayout.types());
        }
        for (FunctionPtrType.Arg arg : typePkg.type().getArgs()) {
            OperationAttr.Operation operation = ((TypeAttr.OperationType) arg.type()).getOperation();
            operation.getFuncOperation().getPrimitiveType().getExtraImportType().ifPresent(imports::addUseImports);
            CommonOperation commonOperation = operation.getCommonOperation();
            commonOperation.getUpperType().typeRefers().forEach(imports::addUseImports);
            commonOperation.makeOperation().typeRefers().forEach(imports::addUseImports);
        }

        imports.addUseImports(CommonTypes.SpecificTypes.FunctionUtils);
        imports.addUseImports(CommonTypes.BasicOperations.Info);
        imports.addUseImports(CommonTypes.BindTypeOperations.PtrOp);
        imports.addUseImports(CommonTypes.FFMTypes.FUNCTION_DESCRIPTOR);
        imports.addUseImports(CommonTypes.FFMTypes.ARENA);
        imports.addUseImports(CommonTypes.FFMTypes.METHOD_HANDLE);
        imports.addUseImports(CommonTypes.FFMTypes.METHOD_HANDLES);
        imports.addUseImports(CommonTypes.FFMTypes.MEMORY_SEGMENT);
        return imports;
    }

    @Override
    public void generate(Dependency dependency) {
        new FuncProtocolGenerator(this, dependency).generate();
    }
}
