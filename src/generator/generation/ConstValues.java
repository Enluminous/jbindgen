package generator.generation;

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
public final class ConstValues implements Generation<TypeAttr.NormalType> {
    private final List<WhenConstruct> construct;
    private final List<TypePkg<TypeAttr.NormalType>> typePkg;

    public ConstValues(PackagePath path, List<TypeAttr.NormalType> types, List<WhenConstruct> constructs) {
        for (TypeAttr.NormalType normalType : types) {
            Assert(normalType instanceof TypeAttr.ValueBased, "type must be ValueBased");
        }
        typePkg = types.stream().map(normalType -> new TypePkg<>(normalType, path.end(normalType.typeName()))).toList();
        this.construct = constructs;
    }

    public interface WhenConstruct {
        String construct(OperationAttr.Operation op);
    }

    @Override
    public Set<TypeAttr.Type> getRefTypes() {
        Set<TypeAttr.Type> types = new HashSet<>();
        for (TypePkg<TypeAttr.NormalType> pkg : typePkg) {
            types.addAll(pkg.type().getReferencedTypes());
        }
        return Set.copyOf(types);
    }

    @Override
    public Set<TypePkg<TypeAttr.NormalType>> getImplTypes() {
        return Set.copyOf(typePkg);
    }
}
