package generator.generation;

import generator.config.PackagePath;
import generator.types.FunctionPtrType;

/**
 * used to generate function pointer, normally used in callback ptr
 */
public final class FuncPointer extends AbstractGeneration<FunctionPtrType> {
    public FuncPointer(PackagePath packagePath, FunctionPtrType type) {
        super(packagePath, type);
    }
}
