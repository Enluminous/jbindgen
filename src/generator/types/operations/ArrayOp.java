package generator.types.operations;

import generator.types.ArrayType;
import generator.types.CommonTypes;
import generator.types.TypeAttr;
import generator.types.TypeImports;

import static generator.generation.generator.CommonGenerator.ARRAY_MAKE_OPERATION_METHOD;

public class ArrayOp implements OperationAttr.MemoryBasedOperation {
    private final String typeName;
    private final ArrayType arrayType;
    private final TypeAttr.OperationType element;

    public ArrayOp(String typeName, ArrayType arrayType) {
        this.arrayType = arrayType;
        this.element = (TypeAttr.OperationType) arrayType.element();
        this.typeName = typeName;
    }

    @Override
    public FuncOperation getFuncOperation() {
        return new FuncOperation() {
            @Override
            public Result destructToPara(String varName) {
                return new Result(varName + ".operator().value()", new TypeImports().addUseImports(arrayType));
            }

            @Override
            public Result constructFromRet(String varName) {
                CommonOperation.Operation operation = element.getOperation().getCommonOperation().makeOperation();
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
                        "%s.asSlice(%s, %s)".formatted(ms, offset, arrayType.byteSize()),
                        element.getOperation().getCommonOperation().makeOperation().str()), new TypeImports().addUseImports(arrayType));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                CommonOperation.UpperType upperType = getCommonOperation().getUpperType();
                return new Setter(upperType.typeName(TypeAttr.NameType.WILDCARD) + " " + varName,
                        "MemoryUtils.memcpy(%s, %s, %s.operator().value(), 0, %s)".formatted(ms, offset, varName, arrayType.byteSize()), upperType.typeImports());

            }
        };
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public Operation makeOperation() {
                Operation eleOp = element.getOperation().getCommonOperation().makeOperation();
                return new Operation(arrayType.typeName(TypeAttr.NameType.RAW) + "." + ARRAY_MAKE_OPERATION_METHOD + "(%s, %s)"
                        .formatted(eleOp.str(), arrayType.length()), eleOp.imports().addUseImports(arrayType));
            }

            @Override
            public UpperType getUpperType() {
                return new Warp<>(CommonTypes.BasicOperations.ArrayI, element.getOperation().getCommonOperation());
            }

            @Override
            public AllocatorType getAllocatorType() {
                return AllocatorType.STANDARD;
            }
        };
    }

}
