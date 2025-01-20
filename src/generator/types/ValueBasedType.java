package generator.types;

import generator.types.operations.OperationAttr;
import generator.types.operations.ValueBased;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class ValueBasedType extends AbstractGenerationType {
    private final CommonTypes.BindTypes bindTypes;

    public ValueBasedType(String typeName, CommonTypes.BindTypes bindTypes) {
        super(bindTypes.getPrimitiveType().getByteSize(), bindTypes.getPrimitiveType().getMemoryLayout(), typeName);
        this.bindTypes = bindTypes;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new ValueBased(typeName, bindTypes.getPrimitiveType());
    }

    public CommonTypes.BindTypes getBindTypes() {
        return bindTypes;
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
        return bindTypes.getUseImportTypes();
    }

    @Override
    public Optional<GenerationTypeHolder<ValueBasedType>> toGenerationTypes() {
        return Optional.of(new GenerationTypeHolder<>(this));
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
        return bindTypes == that.bindTypes;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bindTypes);
    }
}
