package generator.generation;

import analyser.Macro;
import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.generation.generator.MacroGenerator;
import generator.types.TypeAttr;
import generator.types.TypeImports;

import java.util.HashSet;
import java.util.Set;

public class Macros implements Generation<TypeAttr.GenerationType> {
    private final PackagePath packagePath;
    private final Set<Macro> macros;

    public Macros(PackagePath packagePath, HashSet<Macro> macros) {
        this.packagePath = packagePath;
        this.macros = Set.copyOf(macros);
    }

    @Override
    public Set<TypePkg<? extends TypeAttr.GenerationType>> getImplTypes() {
        return Set.of();
    }

    @Override
    public TypeImports getDefineImportTypes() {
        return new TypeImports();
    }

    @Override
    public void generate(Dependency dependency) {
        new MacroGenerator(packagePath, macros, dependency).generate();
    }
}
