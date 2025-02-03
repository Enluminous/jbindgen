package generator.types.operations;

import generator.types.CommonTypes;
import generator.types.StructType;
import generator.types.TypeAttr;

public class MemoryBased implements OperationAttr.MemoryBasedOperation {
    private final String typeName;
    private final StructType structType;
    private final long byteSize;

    public MemoryBased(StructType structType, long byteSize) {
        this.typeName = structType.typeName(TypeAttr.NameType.RAW);
        this.structType = structType;
        this.byteSize = byteSize;
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
                        "%s.asSlice(%s, %s)".formatted(ms, offset, byteSize)));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                String typeName = getCommonOperation().getUpperType().typeName(TypeAttr.NameType.WILDCARD);
                return new Setter(typeName + " " + varName,
                        "MemoryUtils.memcpy(%s, %s, %s.operator().value(), 0, %s)".formatted(ms, offset, varName, byteSize));

            }
        };
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public String makeOperation() {
                return CommonOperation.makeStaticOperation(typeName);
            }

            @Override
            public UpperType getUpperType() {
                End end = new End(structType);
                return new Warp(CommonTypes.BasicOperations.StructI, end);
            }
        };
    }

}
