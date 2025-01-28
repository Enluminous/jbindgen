package generator.generation;

import generator.PackagePath;
import generator.TypePkg;
import generator.types.Holder;
import generator.types.TypeAttr;

import java.util.Set;

public abstract class AbstractGeneration<T extends TypeAttr.GenerationType> implements Generation<T> {
    protected final TypePkg<? extends T> typePkg;

    public AbstractGeneration(PackagePath packagePath, Holder<? extends T> type) {
        typePkg = new TypePkg<>(type, packagePath.end(((TypeAttr.NamedType) type.getT()).typeName(TypeAttr.NameType.RAW)));
    }


    public TypePkg<? extends T> getTypePkg() {
        return typePkg;
    }

    @Override
    public Set<TypePkg<? extends T>> getImplTypes() {
        return Set.of(typePkg);
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        return ((TypeAttr.TypeRefer) typePkg.type()).getDefineImportTypes();
    }

    @Override
    public String toString() {
        return "AbstractGeneration{" +
               "typePkg=" + typePkg +
               '}';
    }
}
