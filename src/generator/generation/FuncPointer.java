package generator.generation;

import generator.config.PackagePath;
import generator.types.FunctionType;

/**
 * used to generate function pointer, normally used in callback ptr
 */
public final class FuncPointer extends AbstractGeneration {
    private final FunctionType function;

    public FuncPointer(PackagePath packagePath, FunctionType function) {
        super(packagePath.end(function.getTypeName()));
        this.function = function;
    }
}
