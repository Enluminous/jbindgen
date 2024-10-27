package generator.generation.generator;

import generator.Dependency;
import generator.generation.Generation;
import generator.types.TypeAttr;

import java.util.Set;
import java.util.stream.Collectors;

public interface Generator {
    static String extractImports(Generation<?> generation, Dependency dependency) {
        return dependency.getTypeImports(generation.getDefineReferTypes()
                .stream().map(TypeAttr.ReferenceType::toGenerationTypes).flatMap(Set::stream).collect(Collectors.toSet()));
    }

    void generate();
}
