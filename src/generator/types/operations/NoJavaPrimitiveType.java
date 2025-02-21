package generator.types.operations;

import generator.types.CommonTypes;
import generator.types.TypeAttr;
import generator.types.TypeImports;

import static utils.CommonUtils.Assert;

public class NoJavaPrimitiveType<T extends TypeAttr.NamedType & TypeAttr.TypeRefer & TypeAttr.OperationType> implements OperationAttr.MemoryBasedOperation {
    private final String typeName;
    private final CommonTypes.BindTypes bindTypes;
    private final T type;
    private final long byteSize;

    public NoJavaPrimitiveType(T type, CommonTypes.BindTypes bindTypes) {
        Assert(bindTypes.getOperations().getValue().getPrimitive().byteSize() == 16);
        this.typeName = type.typeName(TypeAttr.NameType.RAW);
        this.bindTypes = bindTypes;
        this.byteSize = 16;
        this.type = type;
    }

    @Override
    public FuncOperation getFuncOperation() {
        return new FuncOperation() {
            @Override
            public Result destructToPara(String varName) {
                return new Result(varName + ".operator().value()", new TypeImports().addUseImports(type));
            }

            @Override
            public Result constructFromRet(String varName) {
                return new Result("new " + typeName + "(" + varName + ")", new TypeImports().addUseImports(type));
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
                        "%s.asSlice(%s, %s)".formatted(ms, offset, byteSize)), new TypeImports().addUseImports(type));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                CommonOperation.UpperType upperType = getCommonOperation().getUpperType();
                return new Setter(upperType.typeName(TypeAttr.NameType.WILDCARD) + " " + varName,
                        "MemoryUtils.memcpy(%s, %s, %s.operator().value(), 0, %s)".formatted(ms, offset, varName, byteSize),
                        upperType.typeImports());
            }
        };
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public Operation makeOperation() {
                return CommonOperation.makeStaticOperation(type, typeName);
            }

            @Override
            public AllocatorType getAllocatorType() {
                return AllocatorType.ON_HEAP;
            }

            @Override
            public UpperType getUpperType() {
                Reject<?> end = new Reject<>(type);
                return new Warp<>(bindTypes.getOperations().getValue(), end);
            }
        };
    }
}
