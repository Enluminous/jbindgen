package generator.types;

import generator.types.operations.OperationAttr;
import generator.types.operations.PointerOp;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public record PointerType(TypeAttr.TypeRefer pointee) implements
        TypeAttr.SizedType,
        TypeAttr.OperationType,
        TypeAttr.NamedType,
        TypeAttr.TypeRefer {

    @Override
    public OperationAttr.Operation getOperation() {
        return new PointerOp(CommonTypes.BindTypes.makePtrGenericName(((TypeAttr.NamedType) pointee).typeName(TypeAttr.NameType.GENERIC)),
                ((TypeAttr.NamedType) pointee).typeName(TypeAttr.NameType.GENERIC));
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        var types = new HashSet<>(pointee.getUseImportTypes());
        types.addAll(CommonTypes.BindTypes.Ptr.getUseImportTypes());
        return types;
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getUseImportTypes() {
        var types = new HashSet<>(pointee.getUseImportTypes());
        types.addAll(CommonTypes.BindTypes.Ptr.getUseImportTypes());
        return types;
    }

    @Override
    public Optional<? extends Holder<? extends TypeAttr.GenerationType>> toGenerationTypes() {
        return pointee.toGenerationTypes();
    }

    @Override
    public String toString() {
        return "PointerType{" +
               "pointee=" + ((TypeAttr.NamedType) pointee).typeName(TypeAttr.NameType.GENERIC) +
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
    public String typeName(TypeAttr.NameType nameType) {
        return switch (nameType) {
            case WILDCARD ->
                    CommonTypes.BindTypes.makePtrWildcardName(((TypeAttr.NamedType) pointee).typeName(TypeAttr.NameType.WILDCARD));
            case GENERIC ->
                    CommonTypes.BindTypes.makePtrGenericName(((TypeAttr.NamedType) pointee).typeName(TypeAttr.NameType.GENERIC));
            case RAW -> CommonTypes.BindTypes.Ptr.getRawName();
        };
    }
}
