package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.types.FunctionPtrType;

/**
 * used to generate function pointer, normally used in callback ptr
 */
public final class FuncPointer extends AbstractGeneration<FunctionPtrType> {
    public FuncPointer(PackagePath packagePath, FunctionPtrType type) {
        super(packagePath, type);
    }

    @Override
    public void generate(Dependency dependency) {
        System.err.println("todo: generate this");
    }
}
