package generator.types.operations;

import generator.types.ArrayTypeNamed;
import generator.types.CommonTypes;
import generator.types.TypeAttr;

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
            public String destructToPara(String varName) {
                return varName + ".operator().value()";
            }

            @Override
            public String constructFromRet(String varName) {
                return "new %s(%s)".formatted(typeName, varName);
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
                        "%s.asSlice(%s, %s)".formatted(ms, offset, arrayType.byteSize())));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                return new Setter(typeName + " " + varName,
                        "MemoryUtils.memcpy(%s, %s, %s.operator().value(), 0, %s)".formatted(ms, offset, varName, arrayType.byteSize()));

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
        };
    }

}
