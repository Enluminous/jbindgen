package generator.generation.generator;

import generator.Dependency;
import generator.generation.SymbolProvider;

public class SymbolProviderGenerator implements Generator {
    private final SymbolProvider symbolProvider;
    private final Dependency dependency;

    public SymbolProviderGenerator(SymbolProvider symbolProvider, Dependency dependency) {
        this.symbolProvider = symbolProvider;
        this.dependency = dependency;
    }

    @Override
    public void generate() {

    }
}
