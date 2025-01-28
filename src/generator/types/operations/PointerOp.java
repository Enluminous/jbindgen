package generator.types.operations;

import generator.types.CommonTypes;
import generator.types.PointerType;
import generator.types.TypeAttr;

import static generator.TypeNames.MEM_GET;
import static generator.TypeNames.MEM_SET;
import static generator.generation.generator.CommonGenerator.PTR_MAKE_OPERATION_METHOD;

public class PointerOp implements OperationAttr.ValueBasedOperation {
    private final String typeName;
    private final String pointeeName;
    private final PointerType pointerType;
    private final TypeAttr.OperationType pointeeType;

    public PointerOp(String typeName, String pointeeName, PointerType pointerType) {
        this.typeName = typeName;
        this.pointeeName = pointeeName;
        this.pointerType = pointerType;
        pointeeType = (TypeAttr.OperationType) pointerType.pointee();
    }

    @Override
    public FuncOperation getFuncOperation() {
        return new FuncOperation() {
            @Override
            public String destructToPara(String varName) {
                return varName + ".pointer().getMemorySegment()";
            }

            @Override
            public String constructFromRet(String varName) {
                return "new " + typeName + "(" + varName + ")";
            }

            @Override
            public CommonTypes.Primitives getPrimitiveType() {
                return CommonTypes.Primitives.ADDRESS;
            }
        };
    }

    @Override
    public MemoryOperation getMemoryOperation() {
        return new MemoryOperation() {
            @Override
            public Getter getter(String ms, long offset) {
                return new Getter("", typeName, "new %s(%s)".formatted(typeName,
                        MEM_GET.formatted(ms, CommonTypes.Primitives.ADDRESS.getMemoryLayout(), offset)));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                return new Setter(CommonTypes.BindTypes.makePtrWildcardName(pointeeName) + " " + varName,
                        MEM_SET.formatted(ms, CommonTypes.Primitives.ADDRESS.getMemoryLayout(), offset, varName + ".value()"));
            }
        };
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public String makeOperation() {
                return pointerType.typeName(TypeAttr.NameType.RAW) + "." + PTR_MAKE_OPERATION_METHOD + "(%s)"
                        .formatted(pointeeType.getOperation().getCommonOperation().makeOperation());
            }
        };
    }
}
