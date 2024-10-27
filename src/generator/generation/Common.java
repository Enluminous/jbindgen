package generator.generation;

import generator.Dependency;
import generator.TypePkg;
import generator.PackagePath;
import generator.generator.CommonGenerator;
import generator.types.CommonTypes;
import generator.types.TypeAttr;

import java.util.*;

public final class Common implements Generation<CommonTypes.BaseType> {
    public static Common makeBindTypes(PackagePath packagePath) {
        return new Common(Arrays.stream(CommonTypes.BindTypes.values()).map(bindTypes ->
                new TypePkg<CommonTypes.BaseType>(bindTypes, packagePath.end(bindTypes.getRawName()))).toList());
    }

    public static Common makeListTypes(PackagePath packagePath) {
        return new Common(Arrays.stream(CommonTypes.ListTypes.values()).map(bindTypes ->
                new TypePkg<CommonTypes.BaseType>(bindTypes, packagePath.end(bindTypes.getRawName()))).toList());
    }

    public static Common makePrimitives() {
        return new Common(Arrays.stream(CommonTypes.Primitives.values()).map(bindTypes ->
                new TypePkg<CommonTypes.BaseType>(bindTypes,
                        new PackagePath().add("java").add("lang").add("foreign").end("ValueLayout"))
        ).toList());
    }

    public static Common makeSpecific(PackagePath packagePath, CommonTypes.SpecificTypes specificTypes) {
        return new Common(List.of(new TypePkg<>(specificTypes, packagePath.end(specificTypes.name()))));
    }

    public static Common makeSpecific(PackagePath packagePath) {
        return new Common(Arrays.stream(CommonTypes.SpecificTypes.values()).map(bindTypes ->
                new TypePkg<CommonTypes.BaseType>(bindTypes, packagePath.end(bindTypes.name()))).toList());
    }

    private final List<TypePkg<CommonTypes.BaseType>> typePkg;

    public Common(List<TypePkg<CommonTypes.BaseType>> typePkg) {
        this.typePkg = typePkg;
    }

    @Override
    public Set<TypePkg<CommonTypes.BaseType>> getImplTypes() {
        return Set.copyOf(typePkg);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineReferTypes() {
        HashSet<TypeAttr.ReferenceType> types = new HashSet<>();
        for (var function : typePkg) {
            types.addAll(function.type().getDefineReferTypes());
        }
        return types;
    }

    @Override
    public void generate(Dependency dependency) {
        new CommonGenerator(this, dependency).generate();
    }
}
