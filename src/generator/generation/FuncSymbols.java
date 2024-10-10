package generator.generation;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.types.CommonTypes;
import generator.types.FunctionType;
import generator.types.TypeAttr;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * exported function symbol, use {@link Linker#downcallHandle(MemorySegment, FunctionDescriptor, Linker.Option...)} to import symbol
 */
public final class FuncSymbols implements Generation {
    private final List<TypePkg<FunctionType>> functions;
    private final PackagePath packagePath;

    public FuncSymbols(PackagePath packagePath, List<FunctionType> functions) {
        this.packagePath = packagePath;
        this.functions = functions.stream().map(functionType -> new TypePkg<>(functionType, packagePath.end(functionType.getTypeName()))).toList();
    }

    public List<TypePkg<FunctionType>> getFunctions() {
        return functions;
    }

    public PackagePath getPackagePath() {
        return packagePath;
    }

    @Override
    public Set<TypeAttr.Type> getRefTypes() {
        HashSet<TypeAttr.Type> types = new HashSet<>();
        for (var function : functions) {
            types.addAll(function.type().getReferencedTypes());
        }
        types.add(CommonTypes.SpecificTypes.SymbolProvider);
        return Collections.unmodifiableSet(types);
    }

    @Override
    public Set<TypePkg<?>> getImplTypes() {
        return Set.copyOf(functions);
    }
}
