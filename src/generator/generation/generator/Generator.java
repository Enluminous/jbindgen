package generator.generation.generator;

import generator.Dependency;
import generator.generation.Generation;
import generator.types.Holder;
import generator.types.TypeAttr;

import java.util.Optional;
import java.util.stream.Collectors;

public interface Generator {
    static String extractImports(Generation<?> generation, Dependency dependency) {
        return dependency.getTypeImports(generation.getDefineReferTypes().stream()
                .map(TypeAttr.ReferenceType::toGenerationTypes)
                .filter(Optional::isPresent).map(Optional::get)
                .map(Holder::getT).collect(Collectors.toSet()));
    }

    static String getTypeName(TypeAttr.ReferenceType type) {
        return ((TypeAttr.NamedType) type).typeName(TypeAttr.NamedType.NameType.GENERIC);
    }

    void generate();
}
