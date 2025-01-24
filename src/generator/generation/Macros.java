package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.generation.generator.MacroGenerator;
import generator.types.TypeAttr;

import java.util.HashMap;
import java.util.Set;

public class Macros implements Generation<TypeAttr.GenerationType> {
    private final PackagePath packagePath;
    private final HashMap<String, String> macros;

    public Macros(PackagePath packagePath, HashMap<String, String> macros) {
        this.packagePath = packagePath;
        this.macros = macros;
    }

    @Override
    public Set<TypePkg<? extends TypeAttr.GenerationType>> getImplTypes() {
        return Set.of();
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineReferTypes() {
        return Set.of();
    }

    @Override
    public void generate(Dependency dependency) {
        new MacroGenerator(this, dependency).generate();
    }
}
