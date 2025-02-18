package generator.generation.generator;

import generator.types.CommonTypes;
import generator.types.FunctionPtrType;
import generator.types.TypeAttr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static utils.CommonUtils.Assert;

public class FuncPtrUtils {

    public static final String SEGMENT_ALLOCATOR_PARAMETER_NAME = CommonTypes.FFMTypes.SEGMENT_ALLOCATOR.typeName(TypeAttr.NameType.RAW).toLowerCase();

    private FuncPtrUtils() {
        throw new IllegalStateException("Utility class");
    }

    static String makeFuncDescriptor(FunctionPtrType function) {
        List<String> memoryLayout = new ArrayList<>();
        if (function.getReturnType().isPresent())
            memoryLayout.add(((TypeAttr.SizedType) function.getReturnType().get()).getMemoryLayout().memoryLayout());
        memoryLayout.addAll(function.getArgs().stream().map(arg -> ((TypeAttr.SizedType) arg.type()).getMemoryLayout().memoryLayout()).toList());
        var str = String.join(", ", memoryLayout);
        return (function.getReturnType().isPresent()
                ? "FunctionDescriptor.of(%s)"
                : "FunctionDescriptor.ofVoid(%s)").formatted(str);
    }

    static String makeDirectRetType(FunctionPtrType function) {
        return function.getReturnType().map(operationType ->
                        operationType.getOperation().getFuncOperation().getPrimitiveType().getPrimitiveTypeName())
                .orElse("void");
    }

    static String makeWrappedRetType(FunctionPtrType function) {
        return function.getReturnType().map(a -> Generator.getTypeName((TypeAttr.TypeRefer) a))
                .orElse("void");
    }

    static String makeWrappedRetDestruct(String paraStr, FunctionPtrType type) {
        return type.getReturnType().map(operationType -> operationType.getOperation()
                .getFuncOperation().destructToPara(paraStr).codeSegment()).orElse(paraStr);
    }

    static String makeWrappedRetConstruct(String paraStr, FunctionPtrType type) {
        return type.getReturnType().map(operationType -> operationType.getOperation()
                .getFuncOperation().constructFromRet(paraStr).codeSegment()).orElse(paraStr);
    }

    static List<String> makeUpperWrappedParaDestructName(FunctionPtrType function) {
        List<String> para = new ArrayList<>();
        if (function.needAllocator()) {
            para.add(SEGMENT_ALLOCATOR_PARAMETER_NAME);
        }
        for (FunctionPtrType.Arg a : function.getArgs()) {
            TypeAttr.OperationType upperType = (TypeAttr.OperationType) a.type();
            TypeAttr.OperationType type = upperType.getOperation().getCommonOperation().getUpperType().typeOp();
            String destruct = type.getOperation().getFuncOperation().destructToPara(a.argName()).codeSegment();
            para.add(destruct);
        }
        return para;
    }

    static List<String> makeWrappedParaConstructName(FunctionPtrType function) {
        List<String> para = new ArrayList<>();
        if (function.needAllocator()) {
            para.add(SEGMENT_ALLOCATOR_PARAMETER_NAME);
        }
        for (FunctionPtrType.Arg a : function.getArgs()) {
            TypeAttr.OperationType type = (TypeAttr.OperationType) a.type();
            String destruct = type.getOperation().getFuncOperation().constructFromRet(a.argName()).codeSegment();
            para.add(destruct);
        }
        return para;
    }

    static String makeStrBeforeInvoke(FunctionPtrType function) {
        return function.getReturnType().map(normalType -> "return (%s) ".formatted(normalType
                        .getOperation().getFuncOperation().getPrimitiveType().getPrimitiveTypeName()))
                .orElse("");
    }

    static String makeStrForInvoke(String invokeStr, FunctionPtrType function) {
        return function.getReturnType().map(normalType -> "return " + makeWrappedRetConstruct(invokeStr, function))
                .orElse(invokeStr);
    }

    static String makeInvokeStr(FunctionPtrType function) {
        return String.join(", ", makeParaName(function, false));
    }


    static String makeWrappedPara(FunctionPtrType function, boolean ignoreAllocator) {
        List<String> out = new ArrayList<>();
        List<String> type = makeWrappedParaType(function);
        List<String> para = makeParaName(function, ignoreAllocator);
        Assert(type.size() == para.size(), "type.size() != para.size");
        for (int i = 0; i < type.size(); i++) {
            out.add(type.get(i) + " " + para.get(i));
        }
        return String.join(", ", out);
    }

    static String makeUpperWrappedPara(FunctionPtrType function, boolean ignoreAllocator) {
        List<String> out = new ArrayList<>();
        List<String> type = makeUpperWrappedParaType(function);
        List<String> para = makeParaName(function, ignoreAllocator);
        Assert(type.size() == para.size(), "type.size() != para.size");
        for (int i = 0; i < type.size(); i++) {
            out.add(type.get(i) + " " + para.get(i));
        }
        return String.join(", ", out);
    }

    static String makeUpperWrappedParaDestruct(FunctionPtrType function) {
        List<String> para = makeUpperWrappedParaDestructName(function);
        return String.join(", ", para);
    }

    static String makeWrappedParaConstruct(FunctionPtrType function) {
        List<String> para = makeWrappedParaConstructName(function);
        return String.join(", ", para);
    }

    static String makeDirectPara(FunctionPtrType function, boolean ignoreAllocator) {
        List<String> out = new ArrayList<>();
        List<String> type = makeDirectParaType(function, ignoreAllocator);
        List<String> para = makeParaName(function, ignoreAllocator);
        Assert(type.size() == para.size(), "type.size() != para.size");
        for (int i = 0; i < type.size(); i++) {
            out.add(type.get(i) + " " + para.get(i));
        }
        return String.join(", ", out);
    }

    private static List<String> makeWrappedParaType(FunctionPtrType function) {
        List<String> para = new ArrayList<>();
        if (function.needAllocator()) {
            para.add(CommonTypes.FFMTypes.SEGMENT_ALLOCATOR.typeName(TypeAttr.NameType.RAW));
        }
        para.addAll(function.getArgs().stream().map(arg -> Generator.getTypeName(arg.type())).toList());
        return para;
    }

    private static List<String> makeUpperWrappedParaType(FunctionPtrType function) {
        List<String> para = new ArrayList<>();
        if (function.needAllocator()) {
            para.add(CommonTypes.FFMTypes.SEGMENT_ALLOCATOR.typeName(TypeAttr.NameType.RAW));
        }
        para.addAll(function.getArgs().stream().map(arg -> {
            TypeAttr.OperationType upperType = (TypeAttr.OperationType) arg.type();
            return upperType.getOperation().getCommonOperation().getUpperType().typeName(TypeAttr.NameType.WILDCARD);
        }).toList());
        return para;
    }

    private static List<String> makeDirectParaType(FunctionPtrType function, boolean ignoreAllocator) {
        List<String> para = new ArrayList<>();
        if (!ignoreAllocator && function.needAllocator()) {
            para.add(CommonTypes.FFMTypes.SEGMENT_ALLOCATOR.typeName(TypeAttr.NameType.RAW));
        }
        para.addAll(getFuncArgPrimitives(function.getArgs().stream()).map(CommonTypes.Primitives::getPrimitiveTypeName).toList());
        return para;
    }

    private static Stream<CommonTypes.Primitives> getFuncArgPrimitives(Stream<FunctionPtrType.Arg> arg) {
        return arg.map(a -> ((TypeAttr.OperationType) a.type()).getOperation().getFuncOperation().getPrimitiveType());
    }

    private static List<String> makeParaName(FunctionPtrType function, boolean ignoreAllocator) {
        List<String> para = new ArrayList<>();
        if (!ignoreAllocator && function.needAllocator()) {
            para.add(SEGMENT_ALLOCATOR_PARAMETER_NAME);
        }
        para.addAll(function.getArgs().stream().map(FunctionPtrType.Arg::argName).toList());
        return para;
    }
}
