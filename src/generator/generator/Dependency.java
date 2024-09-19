package generator.generator;

import generator.generation.AbstractGeneration;
import generator.types.TypeAttr;

import java.util.*;

public class Dependency {
    private final HashMap<TypeAttr.Type, AbstractGeneration> allGenerations = new HashMap<>();

    public Dependency() {
    }

    public Dependency addGeneration(AbstractGeneration generation) {
        for (TypeAttr.NType selfType : generation.getSelfTypes()) {
            allGenerations.put(selfType, generation);
        }
        return this;
    }

    public Dependency addGeneration(Collection<? extends AbstractGeneration> generation) {
        for (AbstractGeneration gen : generation) {
            for (TypeAttr.NType selfType : gen.getSelfTypes()) {
                allGenerations.put(selfType, gen);
            }
        }
        return this;
    }

    public String getImports(Set<TypeAttr.Type> types) {
        Set<String> imports = new HashSet<>();
        for (TypeAttr.Type type : types) {
            imports.add(allGenerations.get(type).getPackagePath().getImport());
        }
        return String.join("; ", imports);
    }

    public AbstractGeneration getGeneration(TypeAttr.Type type) {
        return allGenerations.get(type);
    }
}
