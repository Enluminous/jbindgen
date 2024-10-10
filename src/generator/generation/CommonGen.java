package generator.generation;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.types.TypeAttr;

import java.util.Set;

public final class CommonGen implements Generation {

    public CommonGen(PackagePath packagePath) {
        // todo
    }

    @Override
    public Set<TypePkg<?>> getImplTypes() {
        return Set.of();
    }

    @Override
    public Set<TypeAttr.Type> getRefTypes() {
        return Set.of();
    }
}
