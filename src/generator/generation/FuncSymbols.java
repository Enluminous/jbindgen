package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.generation.generator.FuncPtrUtils;
import generator.generation.generator.FuncSymbolGenerator;
import generator.types.CommonTypes;
import generator.types.FunctionPtrType;
import generator.types.SymbolProviderType;
import generator.types.TypeAttr;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.util.HashSet;
import java.util.List;
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
                new TypePkg<>(functionType.toGenerationTypes().orElseThrow(),
                        packagePath.removeEnd().add(packagePath.getClassName()).end(functionType.typeName(TypeAttr.NamedType.NameType.GENERIC))
                )).toList();
    }

    public List<TypePkg<FunctionPtrType>> getFunctions() {
        return functions;
    }

    public PackagePath getPackagePath() {
        return packagePath;
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineReferTypes() {
        HashSet<TypeAttr.ReferenceType> types = new HashSet<>();
        for (var function : functions) {
            types.addAll(function.type().getDefineImportTypes());
            FuncPtrUtils.getFuncArgPrimitives(function.type().getArgs().stream()).forEach(p -> {
                if (p.getFfmType() != null)
                    types.addAll(p.getFfmType().getUseImportTypes());
                types.addAll(CommonTypes.FFMTypes.ADDRESS_LAYOUT.getUseImportTypes());
                types.addAll(CommonTypes.FFMTypes.MEMORY_LAYOUT.getUseImportTypes());
            });
            if (function.type().needAllocator()) {
                types.addAll(CommonTypes.FFMTypes.SEGMENT_ALLOCATOR.getUseImportTypes());
            }
        }
        types.addAll(symbolProvider.getUseImportTypes());
        types.addAll(CommonTypes.SpecificTypes.Utils.getUseImportTypes());
        types.addAll(CommonTypes.FFMTypes.METHOD_HANDLE.getUseImportTypes());
        types.addAll(CommonTypes.FFMTypes.FUNCTION_DESCRIPTOR.getUseImportTypes());
        return types;
    }

    @Override
    public void generate(Dependency dependency) {
        new FuncSymbolGenerator(this, dependency, symbolProvider).generate();
    }

    @Override
    public Set<TypePkg<? extends FunctionPtrType>> getImplTypes() {
        return Set.copyOf(functions);
    }
}
