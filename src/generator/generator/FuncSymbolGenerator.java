package generator.generator;

import generator.config.PackagePath;
import generator.generation.FuncSymbol;

import java.util.List;

public class FuncSymbolGenerator extends AbstractGenerator {
    protected FuncSymbolGenerator(PackagePath packagePath) {
        super(packagePath);
    }

    public void generate(List<FuncSymbol> funcSymbol) {

    }

    private static String makeHeader(String basePackageName) {
        return """
                package %1$s.structs;
                
                
                import %1$s.structs.*;
                import %1$s.functions.*;
                import %1$s.shared.values.*;
                import %1$s.shared.*;
                import %1$s.shared.natives.*;
                import %1$s.shared.Value;
                import %1$s.shared.Pointer;
                import %1$s.shared.FunctionUtils;
                
                import java.lang.foreign.*;
                import java.util.function.Consumer;""".formatted(basePackageName);
    }

    private static String makeDirectCall(String funcName, String retType, String funcDescriptor, String symbolClassName,
                                         String strBeforeInvoke, String invokeStr, String para) {
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
