package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.FuncProtocolGenerator;
import generator.generation.generator.FuncPtrUtils;
import generator.types.CommonTypes;
import generator.types.FunctionPtrType;
import generator.types.TypeAttr;
import generator.types.TypeImports;
import generator.types.operations.CommonOperation;

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

        for (FunctionPtrType.Arg arg : typePkg.type().getArgs()) {
            CommonOperation commonOperation = ((TypeAttr.OperationType) arg.type()).getOperation().getCommonOperation();
            commonOperation.getUpperType().typeRefers().forEach(imports::addUseImports);
            commonOperation.makeOperation().typeRefers().forEach(imports::addUseImports);
        }

        imports.addUseImports(CommonTypes.SpecificTypes.FunctionUtils);
        imports.addUseImports(CommonTypes.BasicOperations.Info);
        imports.addUseImports(CommonTypes.BasicOperations.Operation);
        imports.addUseImports(CommonTypes.BindTypeOperations.PtrOp);
        imports.addUseImports(CommonTypes.FFMTypes.FUNCTION_DESCRIPTOR);
        imports.addUseImports(CommonTypes.FFMTypes.ARENA);
        imports.addUseImports(CommonTypes.FFMTypes.METHOD_HANDLE);
        imports.addUseImports(CommonTypes.FFMTypes.METHOD_HANDLES);
        imports.addUseImports(CommonTypes.FFMTypes.MEMORY_LAYOUT);
        imports.addUseImports(CommonTypes.FFMTypes.MEMORY_SEGMENT);

        FuncPtrUtils.getFuncArgPrimitives(typePkg.type().getArgs().stream()).forEach(p -> {
            if (p.getFfmType() != null)
                imports.addUseImports(p.getFfmType());
            imports.addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT);
        });
        return imports;
    }

    @Override
    public void generate(Dependency dependency) {
        new FuncProtocolGenerator(this, dependency).generate();
    }
}
