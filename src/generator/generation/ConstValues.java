package generator.generation;

import generator.config.PackagePath;
import generator.types.TypeAttr;
import generator.types.operations.OperationAttr;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static utils.CommonUtils.Assert;

/**
 * const value like const int XXX
 */
public final class ConstValues extends AbstractGeneration {
    private final List<TypeAttr.NormalType> type;

    private final List<WhenConstruct> construct;

    public ConstValues(PackagePath packagePath, List<TypeAttr.NormalType> types, List<WhenConstruct> constructs) {
        super(packagePath);
        for (TypeAttr.NormalType normalType : types) {
            Assert(normalType instanceof TypeAttr.ValueBased, "type must be ValueBased");
        }
        this.type = types;
        this.construct = constructs;
    }

    public interface WhenConstruct {

        String construct(OperationAttr.Operation op);

    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        Set<TypeAttr.Type> types = new HashSet<>();
        for (TypeAttr.Type type : type) {
            types.addAll(type.getReferencedTypes());
        }
        return Set.copyOf(types);
    }

    @Override
    public Set<TypeAttr.NType> getSelfTypes() {
        return Set.copyOf(type);
    }
}
