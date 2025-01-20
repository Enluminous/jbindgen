package generator.generation;

import generator.Dependency;
import generator.TypePkg;
import generator.PackagePath;
import generator.types.TypeAttr;
import generator.types.operations.OperationAttr;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static utils.CommonUtils.Assert;

/**
 * const value like const int XXX
 */
public final class ConstValues implements Generation<TypeAttr.GenerationType> {
    private final List<WhenConstruct> construct;
    private final List<TypeAttr.ReferenceType> referenceTypes;

    public ConstValues(PackagePath path, List<TypeAttr.ReferenceType> types, List<WhenConstruct> constructs) {
        for (TypeAttr.ReferenceType normalType : types) {
            Assert(normalType instanceof TypeAttr.OperationType operationType
                   && operationType.getOperation() instanceof OperationAttr.ValueBasedOperation,
                    "type must be ValueBased");
        }
        referenceTypes = types;
        this.construct = constructs;
    }

    public interface WhenConstruct {
        String construct(OperationAttr.Operation op);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineReferTypes() {
        Set<TypeAttr.ReferenceType> types = new HashSet<>();
        for (var pkg : referenceTypes) {
            types.addAll(pkg.getDefineImportTypes());
        }
        return types;
    }

    @Override
    public void generate(Dependency dependency) {
        System.err.println("todo: generate this");
    }

    @Override
    public Set<TypePkg<? extends TypeAttr.GenerationType>> getImplTypes() {
        return Set.of();
    }
}
