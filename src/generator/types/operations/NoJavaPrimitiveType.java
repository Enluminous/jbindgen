package generator.types.operations;

import generator.types.CommonTypes;
import generator.types.TypeAttr;

import static utils.CommonUtils.Assert;

public class NoJavaPrimitiveType implements OperationAttr.MemoryBasedOperation {
    private final String typeName;
    private final CommonTypes.BindTypes bindTypes;
    private final long byteSize;

    public NoJavaPrimitiveType(CommonTypes.BindTypes bindTypes) {
        Assert(bindTypes.getOperations().getValue().getPrimitive().getByteSize() == 16);
        this.typeName = bindTypes.typeName(TypeAttr.NameType.RAW);
        this.bindTypes = bindTypes;
        this.byteSize = 16;
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
            public Operation makeOperation() {
                return CommonOperation.makeStaticOperation(typeName);
            }

            @Override
            public UpperType getUpperType() {
                End end = new End(bindTypes, bindTypes.typeName(TypeAttr.NameType.RAW), true);
                return new Warp(bindTypes.getOperations().getValue(), end);
            }
        };
    }

}
