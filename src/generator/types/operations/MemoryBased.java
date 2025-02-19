package generator.types.operations;

import generator.types.*;

public class MemoryBased implements OperationAttr.MemoryBasedOperation {
    private final String typeName;
    private final StructType structType;
    private final long byteSize;

    public MemoryBased(StructType structType, MemoryLayouts memoryLayouts) {
        this.typeName = structType.typeName(TypeAttr.NameType.RAW);
        this.structType = structType;
        this.byteSize = memoryLayouts.getByteSize();
    }

    @Override
    public FuncOperation getFuncOperation() {
        return new FuncOperation() {
            @Override
            public Result destructToPara(String varName) {
                return new Result(varName + ".operator().value()", new TypeImports().addUseImports(structType));
            }

            @Override
            public Result constructFromRet(String varName) {
                return new Result("new " + typeName + "(" + varName + ")", new TypeImports().addUseImports(structType));
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
                        "%s.asSlice(%s, %s)".formatted(ms, offset, byteSize)), new TypeImports().addUseImports(structType));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                CommonOperation.UpperType upperType = getCommonOperation().getUpperType();
                return new Setter(upperType.typeName(TypeAttr.NameType.WILDCARD) + " " + varName,
                        "MemoryUtils.memcpy(%s, %s, %s.operator().value(), 0, %s)".formatted(ms, offset, varName, byteSize), upperType.typeImports());

            }
        };
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public Operation makeOperation() {
                return CommonOperation.makeStaticOperation(structType, typeName);
            }

            @Override
            public UpperType getUpperType() {
                End<?> end = new End<>(structType);
                return new Warp<>(CommonTypes.BasicOperations.StructI, end);
            }

            @Override
            public AllocatorType getAllocatorType() {
                return AllocatorType.STANDARD;
            }
        };
    }

}
