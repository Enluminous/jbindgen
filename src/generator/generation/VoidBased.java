package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.VoidBasedGenerator;
import generator.types.CommonTypes;
import generator.types.Holder;
import generator.types.TypeAttr;
import generator.types.VoidType;

import java.util.HashSet;
import java.util.Set;

public final class VoidBased extends AbstractGeneration<VoidType> {
    public VoidBased(PackagePath packagePath, Holder<VoidType> type) {
        super(packagePath, type);
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        var holders = new HashSet<>(super.getDefineImportTypes());
        holders.addAll(CommonTypes.BasicOperations.Info.getUseImportTypes());
        return holders;
    }

    @Override
    public void generate(Dependency dependency) {
        new VoidBasedGenerator(this, dependency).generate();
    }
}
