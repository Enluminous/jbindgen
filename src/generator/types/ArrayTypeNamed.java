package generator.types;

import generator.Utils;
import generator.types.operations.ArrayNamedOp;
import generator.types.operations.ArrayOp;
import generator.types.operations.OperationAttr;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static utils.CommonUtils.Assert;

public record ArrayTypeNamed(String typeName, long length, TypeAttr.TypeRefer element, long byteSize) implements
        TypeAttr.SizedType, TypeAttr.OperationType, TypeAttr.NamedType, TypeAttr.TypeRefer, TypeAttr.GenerationType {
    public static final CommonTypes.SpecificTypes LIST_TYPE = CommonTypes.SpecificTypes.Array;

    public ArrayTypeNamed {
        Assert(length > 0, "length must be greater than zero");
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new ArrayNamedOp(typeName, this);
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        var types = new HashSet<>(element.getUseImportTypes());
        types.addAll(LIST_TYPE.getUseImportTypes());
        return types;
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getUseImportTypes() {
        return Set.of(new Holder<>(this));
    }

    @Override
    public Optional<Holder<ArrayTypeNamed>> toGenerationTypes() {
        return Optional.of(new Holder<>(this));
    }

    @Override
    public String typeName(TypeAttr.NameType nameType) {
        return typeName;
    }

    @Override
    public String getMemoryLayout() {
        return Utils.makeMemoryLayout(byteSize);
    }

    @Override
    public long getByteSize() {
        return byteSize;
    }
}
