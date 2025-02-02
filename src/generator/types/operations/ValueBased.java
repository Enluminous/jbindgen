package generator.types.operations;

import generator.types.CommonTypes;

public class ValueBased implements OperationAttr.ValueBasedOperation {
    private final String typeName;
    private final CommonTypes.Primitives primitives;

    public ValueBased(String typeName, CommonTypes.Primitives primitives) {
        this.typeName = typeName;
        this.primitives = primitives;
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
                return "new " + typeName + "(" + varName + ")";
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
                return new Getter("", typeName, "new %s(%s)".formatted(typeName,
                        "MemoryUtils.get%s(%s, %s)".formatted(primitives.getMemoryUtilName(), ms, offset)));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                return new Setter(typeName + " " + varName,
                        "MemoryUtils.set%s(%s, %s, %s.operator().value())".formatted(primitives.getMemoryUtilName(), ms, offset, varName));
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
