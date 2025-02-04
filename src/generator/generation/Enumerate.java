package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.EnumGenerator;
import generator.types.CommonTypes;
import generator.types.EnumType;
import generator.types.Holder;
import generator.types.TypeAttr;

import java.util.HashSet;
import java.util.Set;


public final class Enumerate extends AbstractGeneration<EnumType> {
    public Enumerate(PackagePath packagePath, Holder<EnumType> type) {
        super(packagePath, type);
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        var types = new HashSet<>(super.getDefineImportTypes());
        types.addAll(CommonTypes.SpecificTypes.Utils.getUseImportTypes());
        return types;
    }

    @Override
    public void generate(Dependency dependency) {
        new EnumGenerator(this, dependency).generate();
    }
}
