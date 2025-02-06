package generator.generation.generator;

import generator.Dependency;
import generator.generation.Generation;
import generator.types.TypeAttr;

public interface Generator {
    static String extractImports(Generation<?> generation, Dependency dependency) {
        return dependency.getTypeImports(generation.getDefineImportTypes().getImports());
    }

    static String getTypeName(TypeAttr.TypeRefer type) {
        return ((TypeAttr.NamedType) type).typeName(TypeAttr.NameType.GENERIC);
    }

    void generate();
}
