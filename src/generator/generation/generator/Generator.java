package generator.generation.generator;

import generator.Dependency;
import generator.generation.Generation;
import generator.types.Holder;
import generator.types.TypeAttr;

import java.util.Optional;
import java.util.stream.Collectors;

public interface Generator {
    static String extractImports(Generation<?> generation, Dependency dependency) {
        return dependency.getTypeImports(generation.getDefineImportTypes().stream()
                .map(Holder::getT)
                .map(TypeAttr.TypeRefer::toGenerationTypes)
                .filter(Optional::isPresent).map(Optional::get)
                .map(Holder::getT).collect(Collectors.toSet()));
    }

    static String getTypeName(TypeAttr.TypeRefer type) {
        return ((TypeAttr.NamedType) type).typeName(TypeAttr.NamedType.NameType.GENERIC);
    }

    void generate();
}
