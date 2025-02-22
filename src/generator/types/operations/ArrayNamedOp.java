package generator.types.operations;

import generator.types.ArrayTypeNamed;
import generator.types.CommonTypes;
import generator.types.TypeAttr;
import generator.types.TypeImports;

public class ArrayNamedOp implements OperationAttr.MemoryBasedOperation {
    private final String typeName;
    private final ArrayTypeNamed arrayType;
    private final TypeAttr.OperationType element;

    public ArrayNamedOp(String typeName, ArrayTypeNamed arrayType) {
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
                return new Result("new %s(%s)".formatted(typeName, varName), new TypeImports().addUseImports(arrayType));
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
            private final String memoryLayout = getCommonOperation().makeDirectMemoryLayout().getMemoryLayout();

            @Override
            public Getter getter(String ms, long offset) {
                return new Getter("", typeName, "new %s(%s)".formatted(typeName,
                        "%s.asSlice(%s, %s)".formatted(ms, offset, memoryLayout)),
                        new TypeImports().addUseImports(arrayType));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                CommonOperation.UpperType upperType = getCommonOperation().getUpperType();
                return new Setter(upperType.typeName(TypeAttr.NameType.WILDCARD) + " " + varName,
                        "MemoryUtils.memcpy(%s.operator().value(), %s, %s, %s, %s.byteSize())".formatted(
                                varName, 0, ms, offset, memoryLayout),
                        upperType.typeImports().addUseImports(CommonTypes.SpecificTypes.MemoryUtils));
            }
        };
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public Operation makeOperation() {
                return CommonOperation.makeStaticOperation(arrayType, typeName);
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
