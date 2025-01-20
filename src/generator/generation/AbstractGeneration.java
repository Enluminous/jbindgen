package generator.generation;

import generator.TypePkg;
import generator.PackagePath;
import generator.types.TypeAttr;

import java.util.Set;

public abstract class AbstractGeneration<T extends TypeAttr.GenerationType> implements Generation<T> {
    protected final TypePkg<T> typePkg;

    public AbstractGeneration(PackagePath packagePath, T type) {
        typePkg = new TypePkg<>(type, packagePath.end(((TypeAttr.NamedType) type).typeName()));
    }


    public TypePkg<T> getTypePkg() {
        return typePkg;
    }

    @Override
    public Set<TypePkg<? extends T>> getImplTypes() {
        return Set.of(typePkg);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineReferTypes() {
        return ((TypeAttr.ReferenceType) typePkg.type()).getDefineImportTypes();
    }

    @Override
    public String toString() {
        return "AbstractGeneration{" +
               "typePkg=" + typePkg +
               '}';
    }
}
