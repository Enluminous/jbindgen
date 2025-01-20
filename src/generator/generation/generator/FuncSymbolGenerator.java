package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.FuncSymbols;
import generator.types.CommonTypes;
import generator.types.FunctionPtrType;

import static utils.CommonUtils.Assert;

public class FuncSymbolGenerator implements Generator {
    private final FuncSymbols funcSymbols;
    private final Dependency dependency;
    private final String symbolClassName;

    public FuncSymbolGenerator(FuncSymbols funcSymbols, Dependency dependency) {
        this.funcSymbols = funcSymbols;
        this.dependency = dependency;
        this.symbolClassName = dependency.getTypePackagePath(CommonTypes.SpecificTypes.SymbolProvider).getClassName();
    }

    @Override
    public void generate() {
        String out = Generator.extractImports(funcSymbols, dependency);
        for (var symbol : funcSymbols.getFunctions()) {
            FunctionPtrType type = symbol.type();
            out += makeDirectCall(Generator.getTypeName(type), FuncPtrUtils.makeRetType(type), FuncPtrUtils.makeFuncDescriptor(type),
                    symbolClassName, FuncPtrUtils.makeStrBeforeInvoke(type), FuncPtrUtils.makeInvokeStr(type), FuncPtrUtils.makeDirectPara(type));
        }
        Utils.write(funcSymbols.getPackagePath().getFilePath(), out);
    }


    private static String makeDirectCall(String funcName, String retType, String funcDescriptor,
                                         String symbolClassName, String strBeforeInvoke,
                                         String invokeStr, String para) {
        return """
                    private static MethodHandle %1$s;
                
                    private static %2$s %1$s(%7$s) {{
                        if (%1$s == null) {{
                            %1$s = %4$s.toMethodHandle("%1$s", %3$s).orElseThrow(() -> new FunctionUtils.SymbolNotFound("%1$s"));
                        }}
                        try {{
                            %5$s%1$s.invoke(%6$s);
                        }} catch (Throwable e) {{
                            throw new FunctionUtils.InvokeException(e);
                        }}
                    }}
                """.formatted(funcName, retType, funcDescriptor, symbolClassName, strBeforeInvoke, invokeStr, para);
    }
}
