package generator.generation;

import generator.config.PackagePath;
import generator.types.EnumType;
import generator.types.TypeAttr;

import java.util.Set;

/**
 * function parameter, struct etc. used enum
 */
public final class EnumGen extends AbstractGeneration {
    private final EnumType enumType;

    public EnumGen(PackagePath packagePath, EnumType enumType) {
        super(packagePath.end(enumType.getTypeName()));
        this.enumType = enumType;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        return enumType.getReferencedTypes();
    }
}
