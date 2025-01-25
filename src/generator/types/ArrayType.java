package generator.types;

import generator.Utils;
import generator.types.operations.ArrayOp;
import generator.types.operations.OperationAttr;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public record ArrayType(String typeName, long length, TypeAttr.ReferenceType element, long byteSize) implements
        TypeAttr.SizedType, TypeAttr.OperationType, TypeAttr.NamedType, TypeAttr.ReferenceType {
    private static final CommonTypes.SpecificTypes LIST_TYPE = CommonTypes.SpecificTypes.Array;

    public ArrayType(Optional<String> typeName, long length, TypeAttr.ReferenceType element, long byteSize) {
        this(typeName.orElseGet(() -> LIST_TYPE.getGenericName(((TypeAttr.NamedType) element).typeName(NameType.GENERIC))), length, element, byteSize);
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new ArrayOp(typeName, ((TypeAttr.NamedType) element).typeName(NameType.GENERIC), length, byteSize);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
        Set<TypeAttr.ReferenceType> types = new HashSet<>(element.getUseImportTypes());
        types.addAll(LIST_TYPE.getUseImportTypes());
        return types;
    }

    @Override
    public Set<TypeAttr.ReferenceType> getUseImportTypes() {
        Set<TypeAttr.ReferenceType> types = new HashSet<>(element.getUseImportTypes());
        types.addAll(LIST_TYPE.getUseImportTypes());
        return types;
    }

    @Override
    public Optional<? extends Holder<? extends TypeAttr.GenerationType>> toGenerationTypes() {
        return element.toGenerationTypes();
    }

    @Override
    public String typeName(NameType nameType) {
        return switch (nameType) {
            case WILDCARD -> LIST_TYPE.getWildcardName(((TypeAttr.NamedType) element).typeName(NameType.WILDCARD));
            case GENERIC -> LIST_TYPE.getGenericName(((TypeAttr.NamedType) element).typeName(NameType.GENERIC));
            case RAW -> LIST_TYPE.getRawName();
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
