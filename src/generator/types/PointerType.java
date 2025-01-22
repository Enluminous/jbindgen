package generator.types;

import generator.types.operations.OperationAttr;
import generator.types.operations.PointerOp;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public record PointerType(TypeAttr.ReferenceType pointee) implements
        TypeAttr.SizedType,
        TypeAttr.OperationType,
        TypeAttr.NamedType,
        TypeAttr.ReferenceType {

    @Override
    public OperationAttr.Operation getOperation() {
        return new PointerOp(CommonTypes.BindTypes.makePtrGenericName(((TypeAttr.NamedType) pointee).typeName(NameType.GENERIC)),
                ((TypeAttr.NamedType) pointee).typeName(NameType.GENERIC));
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
        Set<TypeAttr.ReferenceType> types = new HashSet<>();
        types.addAll(pointee.getUseImportTypes());
        types.addAll(CommonTypes.BindTypes.Pointer.getUseImportTypes());
        return types;
    }

    @Override
    public Set<TypeAttr.ReferenceType> getUseImportTypes() {
        Set<TypeAttr.ReferenceType> types = new HashSet<>(pointee.getUseImportTypes());
        types.addAll(CommonTypes.BindTypes.Pointer.getUseImportTypes());
        return types;
    }

    @Override
    public Optional<? extends GenerationTypeHolder<? extends TypeAttr.GenerationType>> toGenerationTypes() {
        return pointee.toGenerationTypes();
    }

    @Override
    public String toString() {
        return "PointerType{" +
               "pointee=" + ((TypeAttr.NamedType) pointee).typeName(NameType.GENERIC) +
               '}';
    }

    @Override
    public String getMemoryLayout() {
        return CommonTypes.Primitives.ADDRESS.getMemoryLayout();
    }

    @Override
    public long getByteSize() {
        return CommonTypes.Primitives.ADDRESS.getByteSize();
    }

    @Override
    public String typeName(NameType nameType) {
        return switch (nameType) {
            case WILDCARD ->
                    CommonTypes.BindTypes.makePtrWildcardName(((TypeAttr.NamedType) pointee).typeName(NameType.WILDCARD));
            case GENERIC ->
                    CommonTypes.BindTypes.makePtrGenericName(((TypeAttr.NamedType) pointee).typeName(NameType.GENERIC));
            case RAW -> CommonTypes.BindTypes.Pointer.getRawName();
        };
    }
}
