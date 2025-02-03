package generator.types;

import generator.types.operations.OperationAttr;
import generator.types.operations.ValueBased;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static utils.CommonUtils.Assert;

public final class ValueBasedType extends AbstractGenerationType {
    private final CommonTypes.BindTypes bindTypes;
    private final PointerType pointerType;

    public ValueBasedType(String typeName, CommonTypes.BindTypes bindTypes) {
        super(bindTypes.getPrimitiveType().getByteSize(), bindTypes.getPrimitiveType().getMemoryLayout(), typeName);
        Assert(bindTypes != CommonTypes.BindTypes.Ptr);
        this.bindTypes = bindTypes;
        this.pointerType = null;
    }

    public ValueBasedType(String typeName, PointerType pointerType) {
        super(pointerType.getByteSize(), pointerType.getMemoryLayout(), typeName);
        this.bindTypes = CommonTypes.BindTypes.Ptr;
        this.pointerType = pointerType;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new ValueBased(this, typeName, bindTypes);
    }

    public CommonTypes.BindTypes getBindTypes() {
        return bindTypes;
    }

    public Optional<PointerType> getPointerType() {
        return Optional.ofNullable(pointerType);
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        var ref = new HashSet<>(bindTypes.getUseImportTypes());
        ref.addAll(CommonTypes.FFMTypes.MEMORY_SEGMENT.getUseImportTypes());
        ref.addAll(CommonTypes.FFMTypes.SEGMENT_ALLOCATOR.getUseImportTypes());
        if (pointerType != null)
            ref.addAll(pointerType.getUseImportTypes());
        return ref;
    }

    @Override
    public Optional<Holder<ValueBasedType>> toGenerationTypes() {
        return Optional.of(new Holder<>(this));
    }

    @Override
    public String toString() {
        return "ValueBasedType{" +
                "bindTypes=" + bindTypes +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ValueBasedType that)) return false;
        if (!super.equals(o)) return false;
        return bindTypes == that.bindTypes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bindTypes);
    }
}
