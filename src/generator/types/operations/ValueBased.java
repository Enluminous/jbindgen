package generator.types.operations;

import generator.types.CommonTypes;

import static generator.TypeNames.MEM_GET;
import static generator.TypeNames.MEM_SET;

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
            public Getter getter(String ms, long offset) {
                return new Getter("", name, "new %s(%s)".formatted(name,
                        MEM_GET.formatted(ms, primitives.getMemoryLayout(), offset)));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                return new Setter(name + " " + varName,
                        MEM_SET.formatted(ms, primitives.getMemoryLayout(), offset, varName + ".value()"));
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
