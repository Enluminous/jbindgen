package generator.generation;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.types.FunctionType;
import generator.types.TypeAttr;

import java.util.Set;

/**
 * used to generate function pointer, normally used in callback ptr
 */
public final class FuncPointer implements Generation<FunctionType> {
    private final TypePkg<FunctionType> typePkg;


    public FuncPointer(PackagePath packagePath, FunctionType function) {
        typePkg = new TypePkg<>(function, packagePath.end(function.typeName()));
    }

    @Override
    public Set<TypePkg<FunctionType>> getImplTypes() {
        return Set.of(typePkg);
    }

    @Override
    public Set<TypeAttr.Type> getRefTypes() {
        return typePkg.type().getReferencedTypes();
    }
}
