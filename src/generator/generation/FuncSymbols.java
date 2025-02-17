package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.generation.generator.FuncPtrUtils;
import generator.generation.generator.FuncSymbolGenerator;
import generator.types.*;
import generator.types.operations.CommonOperation;
import generator.types.operations.OperationAttr;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * exported function symbol, use {@link Linker#downcallHandle(MemorySegment, FunctionDescriptor, Linker.Option...)} to import symbol
 */
public final class FuncSymbols implements Generation<FunctionPtrType> {
    private final List<TypePkg<FunctionPtrType>> functions;
    private final PackagePath packagePath;
    private final SymbolProviderType symbolProvider;

    public FuncSymbols(PackagePath packagePath, List<FunctionPtrType> functions, SymbolProviderType symbolProvider) {
        this.packagePath = packagePath.reqClassName();
        this.symbolProvider = symbolProvider;
        this.functions = functions.stream().map(functionType ->
                new TypePkg<>(functionType, packagePath.removeEnd().add(packagePath.getClassName()).end(functionType.typeName(TypeAttr.NameType.GENERIC))
                )).toList();
    }

    public List<TypePkg<FunctionPtrType>> getFunctions() {
        return functions;
    }

    public PackagePath getPackagePath() {
        return packagePath;
    }

    @Override
    public TypeImports getDefineImportTypes() {
        TypeImports imports = symbolProvider.getUseImportTypes();
        for (var function : functions) {
            imports.addDefineImports(function.type());
            if (function.type().needAllocator()) {
                imports.addUseImports(CommonTypes.FFMTypes.SEGMENT_ALLOCATOR);
            }
            FuncPtrUtils.getFuncArgPrimitives(function.type().getArgs().stream()).forEach(p -> {
                if (p.getFfmType() != null) {
                    imports.addUseImports(p.getFfmType());
                }
            });
            for (FunctionPtrType.Arg arg : function.type().getArgs()) {
                OperationAttr.Operation operation = ((TypeAttr.OperationType) arg.type()).getOperation();
                switch (operation) {
                    case OperationAttr.MemoryBasedOperation _ ->
                            imports.addUseImports(CommonTypes.FFMTypes.MEMORY_LAYOUT);
                    case OperationAttr.ValueBasedOperation _ ->
                            imports.addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT);
                    case OperationAttr.DesctructOnlyOperation _, OperationAttr.NoneBasedOperation _ -> {
                    }
                }
                CommonOperation commonOperation = operation.getCommonOperation();
                commonOperation.getUpperType().typeRefers().forEach(imports::addUseImports);
                commonOperation.makeOperation().typeRefers().forEach(imports::addUseImports);
            }
        }
        return imports.addUseImports(CommonTypes.SpecificTypes.FunctionUtils)
                .addUseImports(CommonTypes.FFMTypes.METHOD_HANDLE)
                .addUseImports(CommonTypes.FFMTypes.FUNCTION_DESCRIPTOR);
    }

    @Override
    public void generate(Dependency dependency) {
        new FuncSymbolGenerator(this, dependency, symbolProvider).generate();
    }

    @Override
    public Set<TypePkg<? extends FunctionPtrType>> getImplTypes() {
        return Set.of();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FuncSymbols that)) return false;
        return Objects.equals(functions, that.functions) && Objects.equals(packagePath, that.packagePath) && Objects.equals(symbolProvider, that.symbolProvider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(functions, packagePath, symbolProvider);
    }
}
