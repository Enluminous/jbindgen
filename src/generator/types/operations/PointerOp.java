package generator.types.operations;

import generator.types.CommonTypes;
import generator.types.PointerType;
import generator.types.TypeAttr;
import generator.types.TypeImports;

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
            public Result destructToPara(String varName) {
                return new Result(varName + ".operator().value()", new TypeImports().addUseImports(pointerType));
            }

            @Override
            public Result constructFromRet(String varName) {
                CommonOperation.Operation operation = pointeeType.getOperation().getCommonOperation().makeOperation();
                return new Result("new %s(%s, %s)".formatted(typeName, varName, operation.str()), operation.imports());
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
                        pointeeType.getOperation().getCommonOperation().makeOperation().str()), new TypeImports().addUseImports(pointerType));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                CommonOperation.UpperType upperType = getCommonOperation().getUpperType();
                return new Setter(upperType.typeName(TypeAttr.NameType.WILDCARD) + " " + varName,
                        "MemoryUtils.setAddr(%s, %s, %s.operator().value())".formatted(ms, offset, varName), upperType.typeImports());
            }
        };
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public Operation makeOperation() {
                Operation pointeeOp = pointeeType.getOperation().getCommonOperation().makeOperation();
                return new Operation(pointerType.typeName(TypeAttr.NameType.RAW) + "." + PTR_MAKE_OPERATION_METHOD + "(%s)"
                        .formatted(pointeeOp.str()), pointeeOp.imports().addUseImports(pointerType));
            }

            @Override
            public UpperType getUpperType() {
                return new Warp<>(CommonTypes.ValueInterface.PtrI, pointeeType.getOperation().getCommonOperation());
            }
        };
    }
}
