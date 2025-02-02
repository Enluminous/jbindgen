package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.ArrayNamedGenerator;
import generator.generation.generator.ValueBasedGenerator;
import generator.types.*;

import java.util.HashSet;
import java.util.Set;

public final class ArrayNamed extends AbstractGeneration<ArrayTypeNamed> {
    public ArrayNamed(PackagePath packagePath, Holder<ArrayTypeNamed> type) {
        super(packagePath, type);
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        HashSet<Holder<TypeAttr.TypeRefer>> holders = new HashSet<>(super.getDefineImportTypes());
        holders.addAll(CommonTypes.FFMTypes.MEMORY_SEGMENT.getUseImportTypes());
        holders.addAll(CommonTypes.SpecificTypes.ArrayOp.getUseImportTypes());
        return holders;
    }

    @Override
    public void generate(Dependency dependency) {
        new ArrayNamedGenerator(this, dependency).generate();
    }
}
