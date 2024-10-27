package generator.generation.generator;

import generator.Dependency;
import generator.TypeNames;
import generator.Utils;
import generator.generation.FuncSymbols;
import generator.types.CommonTypes;
import generator.types.FunctionPtrType;
import generator.types.TypeAttr;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        String out = dependency.getTypeImports(
                funcSymbols.getDefineReferTypes().stream()
                        .map(TypeAttr.ReferenceType::toGenerationTypes).flatMap(Set::stream).collect(Collectors.toSet()));
        for (var symbol : funcSymbols.getFunctions()) {
            FunctionPtrType type = symbol.type();
            out += makeDirectCall(type.typeName(), makeRetType(type), makeFuncDescriptor(type),
                    symbolClassName, makeStrBeforeInvoke(type), makeInvokeStr(type), makePara(type));
        }
        Utils.write(funcSymbols.getPackagePath().getFilePath(), out);
    }

    private static String makeRetType(FunctionPtrType function) {
        return function.getReturnType().map(Object::toString).orElse("void");
    }

    private static String makeFuncDescriptor(FunctionPtrType function) {
        List<String> memoryLayout = new ArrayList<>();
        if (function.getReturnType().isPresent())
            memoryLayout.add(((TypeAttr.SizedType) function.getReturnType().get()).getMemoryLayout());
        memoryLayout.addAll(function.getArgs().stream().map(arg -> ((TypeAttr.SizedType) arg.type()).getMemoryLayout()).toList());
        var str = String.join(", ", memoryLayout);
        return (function.getReturnType().isPresent()
                ? "FunctionDescriptor.of(%s)"
                : "FunctionDescriptor.ofVoid(%s)").formatted(str);
    }

    private static String makeStrBeforeInvoke(FunctionPtrType function) {
        return function.getReturnType().map(normalType -> "return (%s)".formatted(normalType
                        .getOperation().getFuncOperation().getPrimitiveType().getPrimitiveTypeName()))
                .orElse("");
    }

    private static String makeInvokeStr(FunctionPtrType function) {
        return String.join(", ", makeParaName(function));
    }

    private static String makePara(FunctionPtrType function) {
        List<String> out = new ArrayList<>();
        List<String> type = makeParaType(function);
        List<String> para = makeParaName(function);
        Assert(type.size() == para.size(), "type.size() != para.size");
        for (int i = 0; i < type.size(); i++) {
            out.add(type.get(i) + " " + para.get(i));
        }
        return String.join(", ", out);
    }

    private static List<String> makeParaType(FunctionPtrType function) {
        List<String> para = new ArrayList<>();
        if (function.needAllocator()) {
            para.add("SegmentAllocator");
        }
        para.addAll(function.getArgs().stream().map(arg -> ((TypeAttr.NamedType) arg.type()).typeName()).toList());
        return para;
    }


    private static List<String> makeParaName(FunctionPtrType function) {
        List<String> para = new ArrayList<>();
        if (function.needAllocator()) {
            para.add(TypeNames.ALLOCATOR_VAR_NAME);
        }
        para.addAll(function.getArgs().stream().map(FunctionPtrType.Arg::argName).toList());
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
