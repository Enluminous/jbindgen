package generator.generator;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.generation.Generation;
import generator.types.TypeAttr;

import java.util.*;

public class Dependency {
    private final HashMap<TypeAttr.Type, PackagePath> allGenerations = new HashMap<>();

    public Dependency() {
    }

    public Dependency addGeneration(Generation generation) {
        for (TypePkg<?> selfType : generation.getImplTypes()) {
            allGenerations.put(selfType.type(), selfType.packagePath());
        }
        return this;
    }

    public Dependency addGeneration(Collection<? extends Generation> generation) {
        for (Generation gen : generation) {
            for (var selfType : gen.getImplTypes()) {
                allGenerations.put(selfType.type(), selfType.packagePath());
            }
        }
        return this;
    }

    public String getImports(Set<TypeAttr.Type> types) {
        Set<String> imports = new HashSet<>();
        for (TypeAttr.Type type : types) {
            imports.add(allGenerations.get(type).getImport());
        }
        return String.join("; ", imports);
    }

    public PackagePath getPackagePath(TypeAttr.Type type) {
        return allGenerations.get(type);
    }
}
