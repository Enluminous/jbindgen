package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.generation.generator.StructGenerator;
import generator.types.GenerationTypeHolder;
import generator.types.CommonTypes;
import generator.types.StructType;
import generator.types.TypeAttr;

import java.util.HashSet;
import java.util.Set;

public final class Structure extends AbstractGeneration<StructType> {
    public Structure(PackagePath packagePath, GenerationTypeHolder<StructType> type) {
        super(packagePath, type);
    }


    public TypePkg<StructType> getStructType() {
        return typePkg;
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineReferTypes() {
        HashSet<TypeAttr.ReferenceType> types = new HashSet<>(super.getDefineReferTypes());
        types.add(CommonTypes.FFMTypes.ADDRESS_LAYOUT);
        types.add(CommonTypes.FFMTypes.MEMORY_LAYOUT);
        types.add(CommonTypes.FFMTypes.MEMORY_SEGMENT);
        types.add(CommonTypes.FFMTypes.SEGMENT_ALLOCATOR);
        return types;
    }

    @Override
    public void generate(Dependency dependency) {
        new StructGenerator(this, dependency).generate();
    }
}
