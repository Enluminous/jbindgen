package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
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
        Utils.write(symbolProvider.getTypePkg().packagePath().getFilePath(), """
                %s
                
                import java.lang.foreign.FunctionDescriptor;
                import java.lang.foreign.SymbolLookup;
                import java.lang.invoke.MethodHandle;
                import java.util.ArrayList;
                import java.util.Optional;
                
                public class %2$s {
                    private %2$s() {
                        throw new UnsupportedOperationException();
                    }
                
                    private static final ArrayList<SymbolLookup> symbolLookups = new ArrayList<>();
                    private static final boolean critical = false;
                
                    public static void addSymbols(SymbolLookup symbolLookup) {
                        symbolLookups.add(symbolLookup);
                    }
                
                    public static Optional<MethodHandle> toMethodHandle(String functionName, FunctionDescriptor functionDescriptor) {
                        return symbolLookups.stream().map(symbolLookup -> Utils.toMethodHandle(symbolLookup, functionName, functionDescriptor, critical))
                                .filter(Optional::isPresent).map(Optional::get).findFirst();
                    }
                }
                """.formatted(symbolProvider.getTypePkg().packagePath().makePackage(), symbolProvider.getTypePkg().packagePath().getClassName()));
    }
}
