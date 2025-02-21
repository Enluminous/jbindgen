package generator.generation.generator;

import generator.types.CommonTypes;
import generator.types.FunctionPtrType;
import generator.types.MemoryLayouts;
import generator.types.TypeAttr;
import generator.types.operations.CommonOperation;

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
        List<String> memoryLayout = function.getMemoryLayouts().stream().map(MemoryLayouts::getMemoryLayout).toList();
        var str = String.join(", ", memoryLayout);
        return (function.getReturnType().isPresent()
                ? "FunctionDescriptor.of(%s)"
                : "FunctionDescriptor.ofVoid(%s)").formatted(str);
    }

    static String makeRawRetType(FunctionPtrType function) {
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

    private static String makeWrappedRetConstruct(String paraStr, FunctionPtrType type) {
        return type.getReturnType().map(operationType -> operationType.getOperation()
                .getFuncOperation().constructFromRet(paraStr).codeSegment()).orElse(paraStr);
    }

    private static String makeAllocatorParameterStr(FunctionPtrType function) {
        return switch (function.allocatorType()) {
            case NONE -> throw new AssertionError("Illegal allocator type");
            case STANDARD -> SEGMENT_ALLOCATOR_PARAMETER_NAME;
            case ON_HEAP -> {
                long byteSize = ((TypeAttr.SizedType) function.getReturnType().orElseThrow()).byteSize();
                if (byteSize % 8 == 0) {
                    yield "(SegmentAllocator) (_, _) -> MemorySegment.ofArray(new long[%s])".formatted(byteSize / 8);
                } else
                    yield "(SegmentAllocator) (_, _) -> MemorySegment.ofArray(new int[%s])".formatted(Math.ceilDiv(byteSize, 4));
            }
        };
    }

    private static List<String> makeUpperWrappedParaDestructName(FunctionPtrType function) {
        List<String> para = new ArrayList<>();
        if (function.allocatorType() == CommonOperation.AllocatorType.STANDARD) {
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

    private static List<String> makeWrappedParaConstructName(FunctionPtrType function) {
        List<String> para = new ArrayList<>();
        if (function.allocatorType() == CommonOperation.AllocatorType.STANDARD) {
            para.add(SEGMENT_ALLOCATOR_PARAMETER_NAME);
        }
        for (FunctionPtrType.Arg a : function.getArgs()) {
            TypeAttr.OperationType type = (TypeAttr.OperationType) a.type();
            String destruct = type.getOperation().getFuncOperation().constructFromRet(a.argName()).codeSegment();
            para.add(destruct);
        }
        return para;
    }

    static String makeRawStrBeforeInvoke(FunctionPtrType function) {
        return function.getReturnType().map(normalType -> "return (%s) ".formatted(normalType
                        .getOperation().getFuncOperation().getPrimitiveType().getPrimitiveTypeName()))
                .orElse("");
    }

    static String makeWrappedStrForInvoke(String invokeStr, FunctionPtrType function) {
        return function.getReturnType().map(_ -> "return " + makeWrappedRetConstruct(invokeStr, function))
                .orElse(invokeStr);
    }

    static String makeRawInvokeStr(FunctionPtrType function) {
        List<String> para = new ArrayList<>();
        if (function.allocatorType() != CommonOperation.AllocatorType.NONE) {
            para.add(makeAllocatorParameterStr(function));
        }
        para.addAll(function.getArgs().stream().map(FunctionPtrType.Arg::argName).toList());
        return String.join(", ", para);
    }

    static String makeParaNameStr(FunctionPtrType function) {
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

    static String makeRawPara(FunctionPtrType function, boolean ignoreAllocator) {
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
        if (function.allocatorType() == CommonOperation.AllocatorType.STANDARD) {
            para.add(CommonTypes.FFMTypes.SEGMENT_ALLOCATOR.typeName(TypeAttr.NameType.RAW));
        }
        para.addAll(function.getArgs().stream().map(arg -> Generator.getTypeName(arg.type())).toList());
        return para;
    }

    private static List<String> makeUpperWrappedParaType(FunctionPtrType function) {
        List<String> para = new ArrayList<>();
        if (function.allocatorType() == CommonOperation.AllocatorType.STANDARD) {
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
        if (!ignoreAllocator && function.allocatorType() == CommonOperation.AllocatorType.STANDARD) {
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
        if (!ignoreAllocator && function.allocatorType() == CommonOperation.AllocatorType.STANDARD) {
            para.add(SEGMENT_ALLOCATOR_PARAMETER_NAME);
        }
        para.addAll(function.getArgs().stream().map(FunctionPtrType.Arg::argName).toList());
        return para;
    }
}
