package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.SymbolProvider;
import generator.types.CommonTypes;

public class SymbolProviderGenerator implements Generator {
    private final SymbolProvider symbolProvider;
    private final Dependency dependency;

    public SymbolProviderGenerator(SymbolProvider symbolProvider, Dependency dependency) {
        this.symbolProvider = symbolProvider;
        this.dependency = dependency;
    }

    @Override
    public void generate() {
        Utils.write(symbolProvider.getTypePkg().packagePath(), """
                %1$s
                
                %3$s
                import java.lang.foreign.FunctionDescriptor;
                import java.lang.foreign.MemorySegment;
                import java.lang.invoke.MethodHandle;
                import java.util.Objects;
                import java.util.Optional;
                
                public class %2$s {
                    private %2$s() {
                        throw new UnsupportedOperationException();
                    }
                
                    public static %4$s.SymbolProvider symbolProvider = null;
                
                    private static final class SymbolProviderHolder {
                        private static final %4$s.SymbolProvider SYMBOL_PROVIDER = Objects.requireNonNull(symbolProvider);
                    }
                
                    public static Optional<MethodHandle> downcallHandle(String functionName, FunctionDescriptor fd) {
                        Optional<%4$s.Symbol> symbol = SymbolProviderHolder.SYMBOL_PROVIDER.provide(functionName);
                        if (symbol.isPresent() && symbol.get() instanceof %4$s.FunctionSymbol(MemorySegment ms, boolean critical)) {
                            return Optional.of(%4$s.downcallHandle(ms, fd, critical));
                        }
                        return Optional.empty();
                    }
                }
                """.formatted(symbolProvider.getTypePkg().packagePath().makePackage(),
                symbolProvider.getTypePkg().packagePath().getClassName(),
                Generator.extractImports(symbolProvider, dependency), // 3
                CommonTypes.SpecificTypes.FunctionUtils.getRawName()));
    }
}
