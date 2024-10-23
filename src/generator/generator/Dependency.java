package generator.generator;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.generation.Generation;
import generator.types.TypeAttr;

import java.util.*;

import static utils.CommonUtils.Assert;

public class Dependency {
    private final HashMap<TypeAttr.Type, PackagePath> allGenerations = new HashMap<>();

    public Dependency() {
    }

    public Dependency addGeneration(Generation<?> generation) {
        for (TypePkg<?> selfType : generation.getImplTypes()) {
            allGenerations.put(selfType.type(), selfType.packagePath());
        }
        return this;
    }

    public Dependency addGeneration(Collection<? extends Generation<?>> generation) {
        for (Generation<?> gen : generation) {
            for (var selfType : gen.getImplTypes()) {
                allGenerations.put(selfType.type(), selfType.packagePath());
            }
        }
        return this;
    }

    public String getTypeImports(Set<TypeAttr.Type> types) {
        Set<String> imports = new HashSet<>();
        for (TypeAttr.Type type : types) {
            imports.add(getPackagePath(type).getImport());
        }
        return String.join(";\n", imports);
    }

    public PackagePath getTypePackagePath(TypeAttr.Type type) {
        return getPackagePath(type);
    }

    private PackagePath getPackagePath(TypeAttr.Type type) {
        Assert(allGenerations.containsKey(type), "missing type generation: " + type);
        return allGenerations.get(type);
    }
}
