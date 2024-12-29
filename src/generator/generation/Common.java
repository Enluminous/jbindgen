package generator.generation;

import generator.Dependency;
import generator.TypePkg;
import generator.PackagePath;
import generator.generation.generator.CommonGenerator;
import generator.types.CommonTypes;
import generator.types.TypeAttr;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.*;

public final class Common implements Generation<CommonTypes.BaseType> {
    public static Common makeBindTypes(PackagePath packagePath) {
        return new Common(Arrays.stream(CommonTypes.BindTypes.values()).map(bindTypes ->
                new TypePkg<CommonTypes.BaseType>(bindTypes, packagePath.end(bindTypes.getRawName()))).toList());
    }

    public static Common makeValueTypes(PackagePath packagePath) {
        return new Common(Arrays.stream(CommonTypes.ValueInterface.values()).map(valueInterface ->
                new TypePkg<CommonTypes.BaseType>(valueInterface, packagePath.end(valueInterface.getTypeName()))).toList());
    }

    public static Common makeListTypes(PackagePath packagePath) {
        return new Common(Arrays.stream(CommonTypes.ListTypes.values()).map(bindTypes ->
                new TypePkg<CommonTypes.BaseType>(bindTypes, packagePath.end(bindTypes.getRawName()))).toList());
    }

    public static Common makeSpecific(PackagePath packagePath, CommonTypes.SpecificTypes specificTypes) {
        return new Common(List.of(new TypePkg<>(specificTypes, packagePath.end(specificTypes.name()))));
    }

    public static Common makeSpecific(PackagePath packagePath) {
        return new Common(Arrays.stream(CommonTypes.SpecificTypes.values()).map(bindTypes ->
                new TypePkg<CommonTypes.BaseType>(bindTypes, packagePath.end(bindTypes.name()))).toList());
    }

    private static PackagePath makePackagePathByClass(Class<?> clazz) {
        String packageName = clazz.getPackageName();
        String simpleName = clazz.getSimpleName();
        PackagePath packagePath = new PackagePath();
        for (String s : Arrays.stream(packageName.split("\\.")).toList()) {
            packagePath = packagePath.add(s);
        }
        return packagePath.end(simpleName);
    }

    public static Common makeInternal() {
        TypePkg<CommonTypes.BaseType> ml = new TypePkg<>(new CommonTypes.InternalType(), makePackagePathByClass(MemoryLayout.class));
        TypePkg<CommonTypes.BaseType> ms = new TypePkg<>(new CommonTypes.InternalType(), makePackagePathByClass(MemorySegment.class));
        TypePkg<CommonTypes.BaseType> vl = new TypePkg<>(new CommonTypes.InternalType(), makePackagePathByClass(ValueLayout.class));

        return new Common(List.of(ml, ms, vl));
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
