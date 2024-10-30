package generator.types.operations;

import generator.types.CommonTypes;

import java.util.List;

public class PointerOp implements OperationAttr.ValueBasedOperation {

    private final String typeName;

    public PointerOp(String typeName) {
        this.typeName = typeName;
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
            public List<Getter> getter(String ms, long offset) {
                //return MEM_CPY.formatted("pointer", 0, ms, offset, CommonTypes.Primitives.ADDRESS.getByteSize());
                return List.of();
            }

            @Override
            public List<Setter> setter(String ms, long offset, String varName) {
                //return MEM_CPY.formatted(ms, offset, "pointer", 0, CommonTypes.Primitives.ADDRESS.getByteSize());
                return List.of();
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
