package generator.generation;

import generator.TypePkg;
import generator.PackagePath;
import generator.types.TypeAttr;

import java.util.Set;

public sealed class AbstractGeneration<T extends TypeAttr.NType> implements Generation<T> permits Array, Enumerate, FuncPointer, RefOnly, StandardPointer, Structure, Value, Void {
    protected final TypePkg<T> typePkg;

    public AbstractGeneration(PackagePath packagePath, T type) {
        typePkg = new TypePkg<>(type, packagePath.end(type.typeName()));
    }

    @Override
    public Set<TypePkg<T>> getImplTypes() {
        return Set.of(typePkg);
    }

    @Override
    public Set<TypeAttr.Type> getRefTypes() {
        return typePkg.type().getReferencedTypes();
    }

    @Override
    public String toString() {
        return "AbstractGeneration{" +
               "typePkg=" + typePkg +
               '}';
    }
}
