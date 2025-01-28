package generator.types.operations;

import generator.types.ArrayType;
import generator.types.CommonTypes;
import generator.types.TypeAttr;

import static generator.TypeNames.MEM_GET;
import static generator.TypeNames.MEM_SET;
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
                //return MEM_CPY.formatted("pointer", 0, ms, offset, byteSize);
                return new Getter("", typeName, "new %s(%s)".formatted(typeName,
                        MEM_GET.formatted(ms, CommonTypes.Primitives.ADDRESS.getMemoryLayout(), offset)));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                //return MEM_CPY.formatted(ms, offset, "pointer", 0, byteSize);
                return new Setter(typeName + " " + varName,
                        MEM_SET.formatted(ms, CommonTypes.Primitives.ADDRESS.getMemoryLayout(), offset, varName + ".value()"));
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
