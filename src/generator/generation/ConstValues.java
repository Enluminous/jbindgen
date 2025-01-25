package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.types.Holder;
import generator.types.TypeAttr;
import generator.types.operations.OperationAttr;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static utils.CommonUtils.Assert;

/**
 * const value like const int XXX
 */
public final class ConstValues implements Generation<TypeAttr.GenerationType> {
    private final List<WhenConstruct> construct;
    private final List<TypeAttr.TypeRefer> values;

    public ConstValues(PackagePath path, List<TypeAttr.TypeRefer> types, List<WhenConstruct> constructs) {
        for (TypeAttr.TypeRefer normalType : types) {
            Assert(normalType instanceof TypeAttr.OperationType operationType
                   && operationType.getOperation() instanceof OperationAttr.ValueBasedOperation,
                    "type must be ValueBased");
        }
        values = types;
        this.construct = constructs;
    }

    public interface WhenConstruct {
        String construct(OperationAttr.Operation op);
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        return values.stream().map(TypeAttr.TypeRefer::getUseImportTypes).flatMap(Set::stream).collect(Collectors.toSet());
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
