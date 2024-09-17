package generator.generation;

import generator.config.PackagePath;
import generator.types.EnumType;

/**
 * function parameter, struct etc. used enum
 */
public final class EnumGen extends AbstractGeneration {
    private final EnumType enumType;

    public EnumGen(PackagePath packagePath, EnumType enumType) {
        super(packagePath.end(enumType.getTypeName()));
        this.enumType = enumType;
    }
}
