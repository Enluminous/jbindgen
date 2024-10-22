package generator.generation;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.types.EnumType;
import generator.types.TypeAttr;

import java.util.Set;

/**
 * function parameter, struct etc. used enum
 */
public final class Enumerate implements Generation<EnumType> {
    private final TypePkg<EnumType> typePkg;

    public Enumerate(PackagePath packagePath, EnumType enumType) {
        typePkg = new TypePkg<>(enumType, packagePath.end(enumType.typeName()));
    }

    @Override
    public Set<TypePkg<EnumType>> getImplTypes() {
        return Set.of(typePkg);
    }

    @Override
    public Set<TypeAttr.Type> getRefTypes() {
        return typePkg.type().getReferencedTypes();
    }
}
