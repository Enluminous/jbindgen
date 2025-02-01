package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.generation.generator.ConstGenerator;
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
    private final PackagePath path;
    private final List<Value> values;

    public record Value(TypeAttr.TypeRefer type, String value, String name) {

    }

    public ConstValues(PackagePath path, List<Value> values) {
        this.path = path;
        this.values = values;
        for (var value : values) {
            Assert(value.type instanceof TypeAttr.OperationType operationType
                            && operationType.getOperation() instanceof OperationAttr.ValueBasedOperation,
                    "type must be ValueBased");
        }
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        return values.stream().map(Value::type).map(TypeAttr.TypeRefer::getUseImportTypes).flatMap(Set::stream).collect(Collectors.toSet());
    }

    @Override
    public void generate(Dependency dependency) {
        new ConstGenerator(path, values).generate();
    }

    @Override
    public Set<TypePkg<? extends TypeAttr.GenerationType>> getImplTypes() {
        return Set.of();
    }
}
