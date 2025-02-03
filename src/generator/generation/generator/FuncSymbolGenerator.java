package generator.generation.generator;

import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.Utils;
import generator.generation.FuncSymbols;
import generator.types.FunctionPtrType;
import generator.types.SymbolProviderType;

import java.util.stream.Collectors;

public class FuncSymbolGenerator implements Generator {
    private final FuncSymbols funcSymbols;
    private final Dependency dependency;
    private final String symbolClassName;

    public FuncSymbolGenerator(FuncSymbols funcSymbols, Dependency dependency, SymbolProviderType symbolProvider) {
        this.funcSymbols = funcSymbols;
        this.dependency = dependency;
        this.symbolClassName = dependency.getTypePackagePath(symbolProvider).getClassName();
    }

    @Override
    public void generate() {
        PackagePath pp = funcSymbols.getPackagePath();
        String out = pp.makePackage();
        out += Generator.extractImports(funcSymbols, dependency);
        out += "public final class %s {\n%s}".formatted(pp.getClassName(),
                funcSymbols.getFunctions().stream().map(TypePkg::type)
                        .map(type -> makeDirectCall(type, symbolClassName) + System.lineSeparator() + makeWrappedCall(type)
                        ).collect(Collectors.joining(System.lineSeparator())));
        Utils.write(pp.getFilePath(), out);
    }


    private static String makeDirectCall(FunctionPtrType type,
                                         String symbolClassName) {
        return """
                    private static MethodHandle %1$s;
                
                    private static %2$s %1$s$Raw(%7$s) {
                        if (%1$s == null) {
                            %1$s = %4$s.downcallHandle("%1$s", %3$s).orElseThrow(() -> new Utils.SymbolNotFound("%1$s"));
                        }
                        try {
                            %5$s%1$s.invoke(%6$s);
                        } catch (Throwable e) {
                            throw new Utils.InvokeException(e);
                        }
                    }
                """.formatted(Generator.getTypeName(type), FuncPtrUtils.makeDirectRetType(type),
                FuncPtrUtils.makeFuncDescriptor(type), symbolClassName, // 4
                FuncPtrUtils.makeStrBeforeInvoke(type), FuncPtrUtils.makeInvokeStr(type),
                FuncPtrUtils.makeDirectPara(type, false)); // 7
    }

    private static String makeWrappedCall(FunctionPtrType type) {
        return """     
                    public static %2$s %1$s(%3$s) {
                        %4$s;
                    }
                """.formatted(Generator.getTypeName(type),
                FuncPtrUtils.makeWrappedRetType(type), FuncPtrUtils.makeUpperWrappedPara(type, false),
                FuncPtrUtils.makeStrForInvoke("%s$Raw(%s)".formatted(Generator.getTypeName(type),
                        FuncPtrUtils.makeUpperWrappedParaDestruct(type)), type)); // 3
    }
}
