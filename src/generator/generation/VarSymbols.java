package generator.generation;

import generator.Dependency;
import generator.TypePkg;
import generator.PackagePath;
import generator.types.TypeAttr;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * exported variable symbol, use {@link Linker#downcallHandle(MemorySegment, FunctionDescriptor, Linker.Option...)} to import symbolL
 */
public final class VarSymbols implements Generation<TypeAttr.GenerationType> {
    private final List<TypeAttr.ReferenceType> normalTypes;

    public VarSymbols(PackagePath packagePath, List<TypeAttr.ReferenceType> normalTypes) {
        this.normalTypes = normalTypes;
    }

    @Override
    public Set<TypePkg<TypeAttr.GenerationType>> getImplTypes() {
        return Set.of();
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineReferTypes() {
        HashSet<TypeAttr.ReferenceType> types = new HashSet<>();
        for (var normalType : normalTypes) {
            types.addAll(normalType.getDefineImportTypes());
        }
        return types;
    }

    @Override
    public void generate(Dependency dependency) {
        System.err.println("todo: generate this");
    }
}
