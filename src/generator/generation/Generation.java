package generator.generation;

import generator.Dependency;
import generator.TypePkg;
import generator.types.TypeAttr;

import java.util.Set;

public interface Generation<T extends TypeAttr.GenerationType> {
    Set<TypePkg<T>> getImplTypes();

    Set<TypeAttr.ReferenceType> getDefineReferTypes();

    void generate(Dependency dependency);
}
