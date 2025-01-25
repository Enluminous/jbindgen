package generator.generation;

import analyser.Macro;
import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.generation.generator.MacroGenerator;
import generator.types.Holder;
import generator.types.TypeAttr;

import java.util.HashSet;
import java.util.Set;

public class Macros implements Generation<TypeAttr.GenerationType> {
    private final PackagePath packagePath;
    private final HashSet<Macro> macros;

    public Macros(PackagePath packagePath, HashSet<Macro> macros) {
        this.packagePath = packagePath;
        this.macros = macros;
    }

    @Override
    public Set<TypePkg<? extends TypeAttr.GenerationType>> getImplTypes() {
        return Set.of();
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        return Set.of();
    }

    @Override
    public void generate(Dependency dependency) {
        new MacroGenerator(packagePath, macros, dependency).generate();
    }
}
