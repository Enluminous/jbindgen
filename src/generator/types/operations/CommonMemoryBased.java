package generator.types.operations;

import generator.types.CommonTypes;

import static generator.TypeNames.MEM_CPY;

public class CommonMemoryBased implements OperationAttr.MemoryBasedOperation {
    private final String typeName;
    private final long byteSize;

    public CommonMemoryBased(String typeName, long byteSize) {
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
    public CopyOperation getMemoryOperation() {
        return new CopyOperation() {
            @Override
            public String copyFromMS(String ms, long offset) {
                return MEM_CPY.formatted("pointer", 0, ms, offset, byteSize);
            }

            @Override
            public String copyToMS(String ms, long offset, String varName) {
                return MEM_CPY.formatted(ms, offset, "pointer", 0, byteSize);
            }
        };
    }
}
