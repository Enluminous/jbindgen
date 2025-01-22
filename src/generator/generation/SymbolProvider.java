package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.SymbolProviderGenerator;
import generator.types.GenerationTypeHolder;
import generator.types.SymbolProviderType;

public class SymbolProvider extends AbstractGeneration<SymbolProviderType> {
    public SymbolProvider(PackagePath packagePath, GenerationTypeHolder<SymbolProviderType> type) {
        super(packagePath, type);
    }

    @Override
    public void generate(Dependency dependency) {
        new SymbolProviderGenerator(this, dependency).generate();
    }
}
