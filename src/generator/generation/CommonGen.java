package generator.generation;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.types.CommonTypes;
import generator.types.TypeAttr;

import java.util.*;

public final class CommonGen implements Generation {
    public static CommonGen makeBindTypes(PackagePath packagePath) {
        return new CommonGen(Arrays.stream(CommonTypes.BindTypes.values()).map(bindTypes ->
                new TypePkg<CommonTypes.BaseType>(bindTypes, packagePath.end(bindTypes.getRawName()))).toList());
    }

    public static CommonGen makeListTypes(PackagePath packagePath) {
        return new CommonGen(Arrays.stream(CommonTypes.ListTypes.values()).map(bindTypes ->
                new TypePkg<CommonTypes.BaseType>(bindTypes, packagePath.end(bindTypes.getRawName()))).toList());
    }

    public static CommonGen makeSpecific(PackagePath packagePath, CommonTypes.SpecificTypes specificTypes) {
        return new CommonGen(List.of(new TypePkg<>(specificTypes, packagePath.end(specificTypes.name()))));
    }

    private final List<TypePkg<CommonTypes.BaseType>> typePkg;

    public CommonGen(List<TypePkg<CommonTypes.BaseType>> typePkg) {
        this.typePkg = typePkg;
    }

    @Override
    public Set<TypePkg<?>> getImplTypes() {
        return Set.copyOf(typePkg);
    }

    @Override
    public Set<TypeAttr.Type> getRefTypes() {
        HashSet<TypeAttr.Type> types = new HashSet<>();
        for (var function : typePkg) {
            types.addAll(function.type().getReferencedTypes());
        }
        return Collections.unmodifiableSet(types);
    }
}
