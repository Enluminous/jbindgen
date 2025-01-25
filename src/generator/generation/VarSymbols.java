package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.types.Holder;
import generator.types.TypeAttr;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * exported variable symbol, use {@link Linker#downcallHandle(MemorySegment, FunctionDescriptor, Linker.Option...)} to import symbolL
 */
public final class VarSymbols implements Generation<TypeAttr.GenerationType> {
    private final List<TypeAttr.TypeRefer> normalTypes;

    public VarSymbols(PackagePath packagePath, List<TypeAttr.TypeRefer> normalTypes) {
        this.normalTypes = normalTypes;
    }

    @Override
    public Set<TypePkg<? extends TypeAttr.GenerationType>> getImplTypes() {
        return Set.of();
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        return normalTypes.stream().map(TypeAttr.TypeRefer::getUseImportTypes).flatMap(Set::stream).collect(Collectors.toSet());
    }

    @Override
    public void generate(Dependency dependency) {
        System.err.println("todo: generate this");
    }
}
