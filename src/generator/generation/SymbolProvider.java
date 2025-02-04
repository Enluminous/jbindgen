package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.SymbolProviderGenerator;
import generator.types.CommonTypes;
import generator.types.Holder;
import generator.types.SymbolProviderType;
import generator.types.TypeAttr;

import java.util.HashSet;
import java.util.Set;

public class SymbolProvider extends AbstractGeneration<SymbolProviderType> {
    public SymbolProvider(PackagePath packagePath, Holder<SymbolProviderType> type) {
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
        new SymbolProviderGenerator(this, dependency).generate();
    }
}
