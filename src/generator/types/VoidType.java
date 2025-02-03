package generator.types;

import generator.types.operations.CommonOpOnly;
import generator.types.operations.OperationAttr;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public record VoidType(String typeName) implements
        TypeAttr.TypeRefer, TypeAttr.NamedType, TypeAttr.GenerationType, TypeAttr.OperationType {
    public VoidType {
        Objects.requireNonNull(typeName, "use VoidType.VOID instead");
    }

    private boolean realVoid() {
        return this.equals(VoidType.VOID);
    }

    public static final VoidType VOID = new VoidType("Void");

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getUseImportTypes() {
        if (realVoid())
            return Set.of();
        return Set.of(new Holder<>(this));
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        return Set.of();
    }

    @Override
    public Optional<Holder<VoidType>> toGenerationTypes() {
        if (realVoid())
            return Optional.empty();
        return Optional.of(new Holder<>(this));
    }

    @Override
    public String typeName(TypeAttr.NameType nameType) {
        return typeName;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new CommonOpOnly(this, realVoid()// no class will generate, inline it
        );
    }
}
