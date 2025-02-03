package generator.types.operations;

import generator.types.CommonTypes;

public class DestructOnlyOp implements OperationAttr.DesctructOnlyOperation {
    private final CommonTypes.Primitives primitives;

    public DestructOnlyOp(CommonTypes.Primitives primitives) {
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
                throw new UnsupportedOperationException();
            }

            @Override
            public CommonTypes.Primitives getPrimitiveType() {
                return primitives;
            }
        };
    }

    @Override
    public MemoryOperation getMemoryOperation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CommonOperation getCommonOperation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
