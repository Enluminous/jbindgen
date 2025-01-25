package generator.generation;

import generator.Dependency;
import generator.TypePkg;
import generator.types.Holder;
import generator.types.TypeAttr;

import java.util.Set;

public interface Generation<T extends TypeAttr.GenerationType> {
    Set<TypePkg<? extends T>> getImplTypes();

    Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes();

    void generate(Dependency dependency);
}
