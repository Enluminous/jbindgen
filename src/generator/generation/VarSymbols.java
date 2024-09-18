package generator.generation;

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
public final class VarSymbols extends AbstractGeneration {
    private final List<TypeAttr.NormalType> normalTypes;

    public VarSymbols(PackagePath packagePath, List<TypeAttr.NormalType> normalTypes) {
        super(packagePath);
        this.normalTypes = normalTypes;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        HashSet<TypeAttr.Type> types = new HashSet<>();
        for (TypeAttr.NormalType normalType : normalTypes) {
            types.addAll(normalType.getReferencedTypes());
        }
        return Collections.unmodifiableSet(types);
    }
}
