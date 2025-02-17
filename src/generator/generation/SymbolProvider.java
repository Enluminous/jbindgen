package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.SymbolProviderGenerator;
import generator.types.CommonTypes;
import generator.types.SymbolProviderType;
import generator.types.TypeImports;

public final class SymbolProvider extends AbstractGeneration<SymbolProviderType> {
    public SymbolProvider(PackagePath packagePath, SymbolProviderType type) {
        super(packagePath, type);
    }

    @Override
    public TypeImports getDefineImportTypes() {
        return super.getDefineImportTypes().addUseImports(CommonTypes.SpecificTypes.FunctionUtils);
    }

    @Override
    public void generate(Dependency dependency) {
        new SymbolProviderGenerator(this, dependency).generate();
    }
}
