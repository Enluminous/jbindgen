package generator.generation;

import analyser.Analyser;
import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.generation.generator.MacroGenerator;
import generator.types.TypeAttr;

import java.util.HashSet;
import java.util.Set;

public class Macros implements Generation<TypeAttr.GenerationType> {
    private final PackagePath packagePath;
    private final HashSet<Analyser.Macro> macros;

    public Macros(PackagePath packagePath, HashSet<Analyser.Macro> macros) {
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
        new MacroGenerator(packagePath, macros, dependency).generate();
    }
}
