package generator.generation;

import generator.config.PackagePath;
import generator.types.TypeAttr;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.util.List;

/**
 * exported variable symbol, use {@link Linker#downcallHandle(MemorySegment, FunctionDescriptor, Linker.Option...)} to import symbolL
 */
public final class VarSymbols extends AbstractGeneration {
    private final List<TypeAttr.NormalType> normalTypes;

    public VarSymbols(PackagePath packagePath, List<TypeAttr.NormalType> normalTypes) {
        super(packagePath);
        this.normalTypes = normalTypes;
    }
}
