package generator.types.operations;

import generator.types.CommonTypes;

import static generator.TypeNames.MEM_GET;
import static generator.TypeNames.MEM_SET;

public class ArrayOp implements OperationAttr.MemoryBasedOperation {
    private final String typeName;
    private final String elementName;
    private final long len;
    private final long byteSize;

    public ArrayOp(String typeName, String elementName, long len, long byteSize) {
        this.typeName = typeName;
        this.elementName = elementName;
        this.len = len;
        this.byteSize = byteSize;
    }

    @Override
    public FuncOperation getFuncOperation() {
        return new FuncOperation() {
            @Override
            public String destructToPara(String varName) {
                return varName + ".pointer().getMemorySegment()";
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
                        MEM_GET.formatted(CommonTypes.Primitives.ADDRESS.getMemoryLayout(), offset)));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                //return MEM_CPY.formatted(ms, offset, "pointer", 0, byteSize);
                return new Setter(typeName + " " + varName,
                        MEM_SET.formatted(CommonTypes.Primitives.ADDRESS.getMemoryLayout(), offset, varName + ".pointer()"));
            }
        };
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public String getTypeName() {
                return typeName;
            }
        };
    }
}
