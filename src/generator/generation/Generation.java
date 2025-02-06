package generator.generation;

import generator.Dependency;
import generator.TypePkg;
import generator.types.TypeAttr;
import generator.types.TypeImports;

import java.util.Set;

public interface Generation<T extends TypeAttr.GenerationType> {
    Set<TypePkg<? extends T>> getImplTypes();

    TypeImports getDefineImportTypes();

    void generate(Dependency dependency);
}
