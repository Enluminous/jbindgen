package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.RefOnlyGenerator;
import generator.types.CommonTypes;
import generator.types.Holder;
import generator.types.RefOnlyType;
import generator.types.TypeAttr;

import java.util.HashSet;
import java.util.Set;

public final class RefOnly extends AbstractGeneration<RefOnlyType> {
    public RefOnly(PackagePath packagePath, Holder<RefOnlyType> type) {
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
        new RefOnlyGenerator(this, dependency).generate();
    }
}
