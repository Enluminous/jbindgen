package generator.types.operations;

import generator.types.CommonTypes;

import java.util.List;

public class FunctionPtrBased implements OperationAttr.ValueBasedOperation {

    private final String typeName;

    public FunctionPtrBased(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public FuncOperation getFuncOperation() {
        return new FuncOperation() {
            @Override
            public String destructToPara(String varName) {
                return varName + ".pointer()";
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
                //return MEM_GET.formatted(CommonTypes.Primitives.ADDRESS.getMemoryLayout(), offset);
                return List.of();
            }

            @Override
            public List<Setter> setter(String ms, long offset, String varName) {
                //return MEM_SET.formatted(CommonTypes.Primitives.ADDRESS.getMemoryLayout(), offset, varName);
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
