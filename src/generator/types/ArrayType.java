package generator.types;

import generator.Utils;
import generator.types.operations.ArrayOp;
import generator.types.operations.OperationAttr;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public record ArrayType(String typeName, long length, TypeAttr.ReferenceType element, long byteSize) implements
        TypeAttr.SizedType, TypeAttr.OperationType, TypeAttr.NamedType, TypeAttr.ReferenceType {
    private static final CommonTypes.SpecificTypes SPECIFIC_TYPES = CommonTypes.SpecificTypes.NList;

    public ArrayType(Optional<String> typeName, long length, TypeAttr.ReferenceType element, long byteSize) {
        this(typeName.orElseGet(() -> SPECIFIC_TYPES.getGenericName(((TypeAttr.NamedType) element).typeName(NameType.GENERIC))), length, element, byteSize);
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new ArrayOp(typeName, ((TypeAttr.NamedType) element).typeName(NameType.GENERIC), length, byteSize);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
        Set<TypeAttr.ReferenceType> types = new HashSet<>(element.getUseImportTypes());
        types.addAll(SPECIFIC_TYPES.getUseImportTypes());
        return types;
    }

    @Override
    public Set<TypeAttr.ReferenceType> getUseImportTypes() {
        Set<TypeAttr.ReferenceType> types = new HashSet<>(element.getUseImportTypes());
        types.addAll(SPECIFIC_TYPES.getUseImportTypes());
        return types;
    }

    @Override
    public Optional<? extends GenerationTypeHolder<? extends TypeAttr.GenerationType>> toGenerationTypes() {
        return element.toGenerationTypes();
    }

    @Override
    public String typeName(NameType nameType) {
        return switch (nameType) {
            case WILDCARD ->
                    CommonTypes.SpecificTypes.NList.getWildcardName(((TypeAttr.NamedType) element).typeName(NameType.WILDCARD));
            case GENERIC ->
                    CommonTypes.SpecificTypes.NList.getGenericName(((TypeAttr.NamedType) element).typeName(NameType.GENERIC));
            case RAW -> CommonTypes.SpecificTypes.NList.getRawName();
        };
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
