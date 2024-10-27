package generator;

import generator.types.TypeAttr;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static utils.CommonUtils.Assert;

public class Dependency {
    private final HashMap<TypeAttr.GenerationType, PackagePath> allGenerations = new HashMap<>();

    public Dependency() {
    }

    public Dependency addType(Collection<? extends TypePkg<?>> typePkgs) {
        for (TypePkg<?> selfType : typePkgs) {
            allGenerations.put(selfType.type(), selfType.packagePath());
        }
        return this;
    }

    public String getTypeImports(Set<TypeAttr.GenerationType> types) {
        Set<String> imports = new HashSet<>();
        for (TypeAttr.GenerationType type : types) {
            imports.add(getPackagePath(type).makeImport());
        }
        return String.join("", imports);
    }

    public PackagePath getTypePackagePath(TypeAttr.GenerationType type) {
        return getPackagePath(type);
    }

    private PackagePath getPackagePath(TypeAttr.GenerationType type) {
        Assert(allGenerations.containsKey(type), "missing type generation: " + type);
        return allGenerations.get(type);
    }
}
