package generator.generation;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.types.EnumType;
import generator.types.TypeAttr;

import java.util.Set;

/**
 * function parameter, struct etc. used enum
 */
public final class EnumGen implements Generation {
    private final TypePkg<EnumType> typePkg;

    public EnumGen(PackagePath packagePath, EnumType enumType) {
        typePkg = new TypePkg<>(enumType, packagePath.end(enumType.getTypeName()));
    }

    @Override
    public Set<TypePkg<?>> getImplTypes() {
        return Set.of(typePkg);
    }

    @Override
    public Set<TypeAttr.Type> getRefTypes() {
        return typePkg.type().getReferencedTypes();
    }
}
