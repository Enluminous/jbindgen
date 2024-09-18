package generator.generation;

import generator.config.PackagePath;
import generator.types.TypeAttr;
import generator.types.operations.OperationAttr;

import java.util.Set;

import static utils.CommonUtils.Assert;

/**
 * const value like const int XXX
 */
public final class ConstValues extends AbstractGeneration {
    private final TypeAttr.NormalType type;

    private final WhenConstruct construct;

    public ConstValues(PackagePath packagePath, TypeAttr.NormalType type, WhenConstruct construct) {
        super(packagePath);
        Assert(type.isValueBased());
        this.type = type;
        this.construct = construct;
    }

    public interface WhenConstruct {

        String construct(OperationAttr.Operation op);

    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        return type.getReferencedTypes();
    }
}
