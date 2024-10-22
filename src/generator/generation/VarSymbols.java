package generator.generation;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.types.TypeAttr;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * exported variable symbol, use {@link Linker#downcallHandle(MemorySegment, FunctionDescriptor, Linker.Option...)} to import symbolL
 */
public final class VarSymbols implements Generation<TypeAttr.NormalType> {
    private final List<TypePkg<TypeAttr.NormalType>> normalTypes;

    public VarSymbols(PackagePath packagePath, List<TypeAttr.NormalType> normalTypes) {
        this.normalTypes = normalTypes.stream().map(normalType -> new TypePkg<>(normalType, packagePath.end(normalType.typeName()))).toList();
    }

    @Override
    public Set<TypePkg<TypeAttr.NormalType>> getImplTypes() {
        return Set.copyOf(normalTypes);
    }

    @Override
    public Set<TypeAttr.Type> getRefTypes() {
        HashSet<TypeAttr.Type> types = new HashSet<>();
        for (var normalType : normalTypes) {
            types.addAll(normalType.type().getReferencedTypes());
        }
        return Collections.unmodifiableSet(types);
    }
}
