package generator.types.operations;

import generator.types.CommonTypes;

import java.util.List;

public class ValueBased implements OperationAttr.ValueBasedOperation {
    private final String name;
    private final CommonTypes.Primitives primitives;

    public ValueBased(String typeName, CommonTypes.Primitives primitives) {
        this.name = typeName;
        this.primitives = primitives;
    }

    @Override
    public FuncOperation getFuncOperation() {
        return new FuncOperation() {
            @Override
            public String destructToPara(String varName) {
                return varName + ".value()";
            }

            @Override
            public String constructFromRet(String varName) {
                return "new " + name + "(" + varName + ")";
            }

            @Override
            public CommonTypes.Primitives getPrimitiveType() {
                return primitives;
            }
        };
    }

    @Override
    public MemoryOperation getMemoryOperation() {
        return new MemoryOperation() {
            @Override
            public List<Getter> getter(String ms, long offset) {
                //return MEM_GET.formatted(primitives.getMemoryLayout(), offset);
                return List.of();
            }

            @Override
            public List<Setter> setter(String ms, long offset, String varName) {
                //return MEM_SET.formatted(primitives.getMemoryLayout(), offset, varName);
                return List.of();
            }
        };
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public String getTypeName() {
                return name;
            }
        };
    }
}
