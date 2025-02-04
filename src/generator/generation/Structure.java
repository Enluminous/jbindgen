package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.StructGenerator;
import generator.types.CommonTypes;
import generator.types.Holder;
import generator.types.StructType;
import generator.types.TypeAttr;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class Structure extends AbstractGeneration<StructType> {
    public Structure(PackagePath packagePath, Holder<StructType> type) {
        super(packagePath, type);
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        var types = new HashSet<>(super.getDefineImportTypes());
        types.addAll(CommonTypes.FFMTypes.MEMORY_SEGMENT.getUseImportTypes());
        types.addAll(CommonTypes.FFMTypes.SEGMENT_ALLOCATOR.getUseImportTypes());
        types.addAll(CommonTypes.SpecificTypes.MemoryUtils.getUseImportTypes());
        types.addAll(getTypePkg().type().getMembers().stream().map(StructType.Member::type)
                .map(typeRefer -> ((TypeAttr.OperationType) typeRefer).getOperation()
                        .getCommonOperation().getUpperType().typeRefers()).flatMap(Collection::stream)
                .map(TypeAttr.TypeRefer::getUseImportTypes).flatMap(Collection::stream).collect(Collectors.toSet()));        types.addAll(getTypePkg().type().getMembers().stream().map(StructType.Member::type)
                .map(typeRefer -> ((TypeAttr.OperationType) typeRefer).getOperation()
                        .getCommonOperation().makeOperation().typeRefers()).flatMap(Collection::stream)
                .map(TypeAttr.TypeRefer::getUseImportTypes).flatMap(Collection::stream).collect(Collectors.toSet()));
        return types;
    }

    @Override
    public void generate(Dependency dependency) {
        new StructGenerator(this, dependency).generate();
    }
}
