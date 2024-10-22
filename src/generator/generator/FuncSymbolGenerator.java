package generator.generator;

import generator.TypeNames;
import generator.Utils;
import generator.generation.FuncSymbols;
import generator.types.CommonTypes;
import generator.types.FunctionType;

import java.util.ArrayList;
import java.util.List;

import static utils.CommonUtils.Assert;

public class FuncSymbolGenerator {
    private final FuncSymbols funcSymbols;
    private final Dependency dependency;
    private final String symbolClassName;

    public FuncSymbolGenerator(FuncSymbols funcSymbols, Dependency dependency) {
        this.funcSymbols = funcSymbols;
        this.dependency = dependency;
        this.symbolClassName = dependency.getTypePackagePath(CommonTypes.SpecificTypes.SymbolProvider).getClassName();
    }

    public void generate() {
        String out = dependency.getTypeImports(funcSymbols.getRefTypes());
        for (var symbol : funcSymbols.getFunctions()) {
            FunctionType type = symbol.type();
            out += makeDirectCall(type.typeName(), makeRetType(type), makeFuncDescriptor(type),
                    symbolClassName, makeStrBeforeInvoke(type), makeInvokeStr(type), makePara(type));
        }
        Utils.write(funcSymbols.getPackagePath().getPath(), out);
    }

    private static String makeRetType(FunctionType function) {
        return function.getReturnType().map(Object::toString).orElse("void");
    }

    private static String makeFuncDescriptor(FunctionType function) {
        List<String> memoryLayout = new ArrayList<>();
        if (function.getReturnType().isPresent())
            memoryLayout.add(function.getReturnType().get().getMemoryLayout());
        memoryLayout.addAll(function.getArgs().stream().map(arg -> arg.type().getMemoryLayout()).toList());
        var str = String.join(", ", memoryLayout);
        return (function.getReturnType().isPresent()
                ? "FunctionDescriptor.of(%s)"
                : "FunctionDescriptor.ofVoid(%s)").formatted(str);
    }

    private static String makeStrBeforeInvoke(FunctionType function) {
        return function.getReturnType().map(normalType -> "return (%s)".formatted(normalType
                        .getOperation().getFuncOperation().getPrimitiveType().getPrimitiveTypeName()))
                .orElse("");
    }

    private static String makeInvokeStr(FunctionType function) {
        return String.join(", ", makeParaName(function));
    }

    private static String makePara(FunctionType function) {
        List<String> out = new ArrayList<>();
        List<String> type = makeParaType(function);
        List<String> para = makeParaName(function);
        Assert(type.size() == para.size(), "type.size() != para.size");
        for (int i = 0; i < type.size(); i++) {
            out.add(type.get(i) + " " + para.get(i));
        }
        return String.join(", ", out);
    }

    private static List<String> makeParaType(FunctionType function) {
        List<String> para = new ArrayList<>();
        if (function.needAllocator()) {
            para.add("SegmentAllocator");
        }
        para.addAll(function.getArgs().stream().map(arg -> arg.type().typeName()).toList());
        return para;
    }


    private static List<String> makeParaName(FunctionType function) {
        List<String> para = new ArrayList<>();
        if (function.needAllocator()) {
            para.add(TypeNames.ALLOCATOR_VAR_NAME);
        }
        para.addAll(function.getArgs().stream().map(FunctionType.Arg::argName).toList());
        return para;
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
