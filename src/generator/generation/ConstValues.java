package generator.generation;

import generator.config.PackagePath;
import generator.types.TypeAttr;
import generator.types.operations.OperationAttr;

/**
 * const value like const int XXX
 */
public final class ConstValues extends AbstractGeneration {
    private final TypeAttr.ValueBased type;

    private final WhenConstruct construct;

    public ConstValues(PackagePath packagePath, TypeAttr.ValueBased type, WhenConstruct construct) {
        super(packagePath);
        this.type = type;
        this.construct = construct;
    }

    public interface WhenConstruct {

        String construct(OperationAttr.Operation op);
    }

}
