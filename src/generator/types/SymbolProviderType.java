package generator.types;

import java.util.Objects;

public record SymbolProviderType(
        String className) implements TypeAttr.TypeRefer, TypeAttr.GenerationType, TypeAttr.NamedType {

    public SymbolProviderType {
        Objects.requireNonNull(className);
    }

    @Override
    public TypeImports getUseImportTypes() {
        return new TypeImports(this);
    }

    @Override
    public TypeImports getDefineImportTypes() {
        return new TypeImports();
    }

    @Override
    public String typeName(TypeAttr.NameType nameType) {
        return className;
    }
}
