package generator.types.operations;

import generator.types.ArrayType;
import generator.types.CommonTypes;
import generator.types.TypeAttr;

import static generator.generation.generator.CommonGenerator.ARRAY_MAKE_OPERATION_METHOD;

public class ArrayOp implements OperationAttr.MemoryBasedOperation {
    private final String typeName;
    private final ArrayType arrayType;
    private final TypeAttr.OperationType element;

    public ArrayOp(String typeName, ArrayType arrayType) {
        this.typeName = typeName;
        this.arrayType = arrayType;
        this.element = (TypeAttr.OperationType) arrayType.element();
    }

    @Override
    public FuncOperation getFuncOperation() {
        return new FuncOperation() {
            @Override
            public String destructToPara(String varName) {
                return varName + ".value().getMemorySegment()";
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
                return new Getter("", typeName, "new %s(%s, %s)".formatted(typeName,
                        "%s.asSlice(%s, %s)".formatted(ms, offset, arrayType.byteSize()),
                        element.getOperation().getCommonOperation().makeOperation()));
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
                return arrayType.typeName(TypeAttr.NameType.RAW) + "." + ARRAY_MAKE_OPERATION_METHOD + "(%s, %s)"
                        .formatted(element.getOperation().getCommonOperation().makeOperation(), arrayType.length());
            }
        };
    }

}
