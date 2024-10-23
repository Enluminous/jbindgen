package generator.generation;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.types.FunctionPtrType;
import generator.types.TypeAttr;

import java.util.Set;

/**
 * used to generate function pointer, normally used in callback ptr
 */
public final class FuncPointer implements Generation<FunctionPtrType> {
    private final TypePkg<FunctionPtrType> typePkg;


    public FuncPointer(PackagePath packagePath, FunctionPtrType function) {
        typePkg = new TypePkg<>(function, packagePath.end(function.typeName()));
    }

    @Override
    public Set<TypePkg<FunctionPtrType>> getImplTypes() {
        return Set.of(typePkg);
    }

    @Override
    public Set<TypeAttr.Type> getRefTypes() {
        return typePkg.type().getReferencedTypes();
    }
}
