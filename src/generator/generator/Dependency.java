package generator.generator;

import generator.generation.AbstractGeneration;
import generator.types.TypeAttr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Dependency {
    private final HashMap<TypeAttr.Type, AbstractGeneration> generated = new HashMap<>();
    private final ArrayList<AbstractGeneration> allGenerations;

    public Dependency(ArrayList<AbstractGeneration> allGenerations) {
        this.allGenerations = allGenerations;
    }


    public String getImports(Set<TypeAttr.Type> types) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
