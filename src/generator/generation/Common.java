package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.generation.generator.CommonGenerator;
import generator.types.CommonTypes;
import generator.types.TypeAttr;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Common implements Generation<CommonTypes.BaseType> {
    public static Common makeBindTypes(PackagePath packagePath) {
        return new Common(Arrays.stream(CommonTypes.BindTypes.values()).filter(b -> !b.getPrimitiveType().isDisabled())
                .map(bindTypes -> new TypePkg<>(bindTypes.toGenerationTypes().orElseThrow(), packagePath.end(bindTypes.getRawName()))).toList());
    }

    public static Common makeValueInterfaces(PackagePath packagePath) {
        return new Common(Arrays.stream(CommonTypes.ValueInterface.values()).filter(v -> !v.getPrimitive().isDisabled())
                .map(valueInterface -> new TypePkg<>(valueInterface.toGenerationTypes().orElseThrow(),
                        packagePath.end(valueInterface.typeName(TypeAttr.NamedType.NameType.RAW)))).toList());
    }

    public static Common makeBasicOperations(PackagePath packagePath) {
        return new Common(Arrays.stream(CommonTypes.BasicOperations.values())
                .map(types -> new TypePkg<>(types.toGenerationTypes().orElseThrow(),
                        packagePath.end(types.typeName(TypeAttr.NamedType.NameType.RAW)))).toList());
    }

    public static Common makeBindTypeInterface(PackagePath packagePath) {
        return new Common(Arrays.stream(CommonTypes.BindTypeOperations.values()).filter(l -> !l.getValue().getPrimitive().isDisabled())
                .map(bindTypes -> new TypePkg<>(bindTypes.toGenerationTypes().orElseThrow(),
                        packagePath.end(bindTypes.typeName(TypeAttr.NamedType.NameType.RAW)))).toList());
    }

    public static Common makeSpecific(PackagePath packagePath, CommonTypes.SpecificTypes specificTypes) {
        return new Common(List.of(new TypePkg<>(specificTypes.toGenerationTypes().orElseThrow(), packagePath.end(specificTypes.name()))));
    }

    public static Common makeSpecific(PackagePath packagePath) {
        return new Common(Arrays.stream(CommonTypes.SpecificTypes.values()).map(bindTypes ->
                new TypePkg<>(bindTypes.toGenerationTypes().orElseThrow(), packagePath.end(bindTypes.name()))).toList());
    }

    public static Common makeFFMs() {
        return new Common(Arrays.stream(CommonTypes.FFMTypes.values()).map(bindTypes ->
                new TypePkg<>(bindTypes.toGenerationTypes().orElseThrow(), makePackagePathByClass(bindTypes.getType()))).toList());
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

    private final List<? extends TypePkg<? extends CommonTypes.BaseType>> typePkg;

    public Common(List<? extends TypePkg<? extends CommonTypes.BaseType>> typePkg) {
        this.typePkg = typePkg;
    }

    @Override
    public Set<TypePkg<? extends CommonTypes.BaseType>> getImplTypes() {
        return Set.copyOf(typePkg);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineReferTypes() {
        HashSet<TypeAttr.ReferenceType> types = new HashSet<>();
        for (var function : typePkg) {
            types.addAll(function.type().getDefineImportTypes());
        }
        return types;
    }

    @Override
    public void generate(Dependency dependency) {
        new CommonGenerator(this, dependency).generate();
    }
}
