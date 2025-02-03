package generator.types.operations;

import generator.types.CommonTypes;
import generator.types.PointerType;
import generator.types.TypeAttr;

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
                return varName + ".operator().value()";
            }

            @Override
            public String constructFromRet(String varName) {
                return "new %s(%s, %s)".formatted(typeName, varName, pointeeType.getOperation().getCommonOperation().makeOperation());
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
                return new Getter("", typeName, "new %s(%s, %s)".formatted(typeName,
                        "MemoryUtils.getAddr(%s, %s)".formatted(ms, offset),
                        pointeeType.getOperation().getCommonOperation().makeOperation()));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                return new Setter(typeName + " " + varName,
                        "MemoryUtils.setAddr(%s, %s, %s.operator().value())".formatted(ms, offset, varName));
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

            @Override
            public UpperType getUpperType() {
                return new Warp(CommonTypes.ValueInterface.PtrI, pointeeType.getOperation().getCommonOperation());
            }
        };
    }
}
