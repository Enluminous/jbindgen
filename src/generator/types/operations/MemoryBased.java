package generator.types.operations;

import generator.types.CommonTypes;

import static generator.TypeNames.MEM_GET;
import static generator.TypeNames.MEM_SET;

public class MemoryBased implements OperationAttr.MemoryBasedOperation {
    private final String typeName;
    private final long byteSize;

    public MemoryBased(String typeName, long byteSize) {
        this.typeName = typeName;
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

}
