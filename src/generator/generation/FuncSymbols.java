package generator.generation;

import generator.config.PackagePath;
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
public final class FuncSymbols extends AbstractGeneration {
    private final List<FunctionType> functions;

    public FuncSymbols(PackagePath packagePath, List<FunctionType> functions) {
        super(packagePath);
        this.functions = functions;
    }

    public List<FunctionType> getFunctions() {
        return functions;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        HashSet<TypeAttr.Type> types = new HashSet<>();
        for (FunctionType function : functions) {
            types.addAll(function.getReferencedTypes());
        }
        return Collections.unmodifiableSet(types);
    }
}
