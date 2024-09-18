package generator.generation;

import generator.config.PackagePath;
import generator.types.FunctionType;
import generator.types.TypeAttr;

import java.util.Set;

/**
 * used to generate function pointer, normally used in callback ptr
 */
public final class FuncPointer extends AbstractGeneration {
    private final FunctionType function;

    public FuncPointer(PackagePath packagePath, FunctionType function) {
        super(packagePath.end(function.getTypeName()));
        this.function = function;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        return function.getReferencedTypes();
    }
}
