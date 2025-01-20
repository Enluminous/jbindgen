package generator.generation.generator;

import generator.TypeNames;
import generator.types.CommonTypes;
import generator.types.FunctionPtrType;
import generator.types.TypeAttr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static utils.CommonUtils.Assert;

public class FuncPtrUtils {
    private FuncPtrUtils() {
        throw new IllegalStateException("Utility class");
    }

    static String makeFuncDescriptor(FunctionPtrType function) {
        List<String> memoryLayout = new ArrayList<>();
        if (function.getReturnType().isPresent())
            memoryLayout.add(((TypeAttr.SizedType) function.getReturnType().get()).getMemoryLayout());
        memoryLayout.addAll(function.getArgs().stream().map(arg -> ((TypeAttr.SizedType) arg.type()).getMemoryLayout()).toList());
        var str = String.join(", ", memoryLayout);
        return (function.getReturnType().isPresent()
                ? "FunctionDescriptor.of(%s)"
                : "FunctionDescriptor.ofVoid(%s)").formatted(str);
    }

    static String makeRetType(FunctionPtrType function) {
        return function.getReturnType().map(operationType ->
                        operationType.getOperation().getFuncOperation().getPrimitiveType().getPrimitiveTypeName())
                .orElse("void");
    }

    static String makeStrBeforeInvoke(FunctionPtrType function) {
        return function.getReturnType().map(normalType -> "return (%s)".formatted(normalType
                        .getOperation().getFuncOperation().getPrimitiveType().getPrimitiveTypeName()))
                .orElse("");
    }

    static String makeInvokeStr(FunctionPtrType function) {
        return String.join(", ", makeParaName(function));
    }

    static String makeWrappedPara(FunctionPtrType function) {
        List<String> out = new ArrayList<>();
        List<String> type = makeWrappedParaType(function);
        List<String> para = makeParaName(function);
        Assert(type.size() == para.size(), "type.size() != para.size");
        for (int i = 0; i < type.size(); i++) {
            out.add(type.get(i) + " " + para.get(i));
        }
        return String.join(", ", out);
    }

    static String makeDirectPara(FunctionPtrType function) {
        List<String> out = new ArrayList<>();
        List<String> type = makeDirectParaType(function);
        List<String> para = makeParaName(function);
        Assert(type.size() == para.size(), "type.size() != para.size");
        for (int i = 0; i < type.size(); i++) {
            out.add(type.get(i) + " " + para.get(i));
        }
        return String.join(", ", out);
    }

    static List<String> makeWrappedParaType(FunctionPtrType function) {
        List<String> para = new ArrayList<>();
        if (function.needAllocator()) {
            para.add("SegmentAllocator");
        }
        para.addAll(function.getArgs().stream().map(arg -> Generator.getTypeName(arg.type())).toList());
        return para;
    }

    static List<String> makeDirectParaType(FunctionPtrType function) {
        List<String> para = new ArrayList<>();
        if (function.needAllocator()) {
            para.add("SegmentAllocator");
        }
        para.addAll(getFuncArgPrimitives(function.getArgs().stream()).map(CommonTypes.Primitives::getPrimitiveTypeName).toList());
        return para;
    }

    public static Stream<CommonTypes.Primitives> getFuncArgPrimitives(Stream<FunctionPtrType.Arg> arg) {
        return arg.map(a -> ((TypeAttr.OperationType) a.type()).getOperation().getFuncOperation().getPrimitiveType());
    }

    static List<String> makeParaName(FunctionPtrType function) {
        List<String> para = new ArrayList<>();
        if (function.needAllocator()) {
            para.add(TypeNames.ALLOCATOR_VAR_NAME);
        }
        para.addAll(function.getArgs().stream().map(FunctionPtrType.Arg::argName).toList());
        return para;
    }
}
