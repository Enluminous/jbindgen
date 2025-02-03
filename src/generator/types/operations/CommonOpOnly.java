package generator.types.operations;

import generator.types.CommonTypes;
import generator.types.TypeAttr;

public class CommonOpOnly implements OperationAttr.NoneBasedOperation {
    private final String typeName;
    private final TypeAttr.NamedType namedType;
    private final boolean realVoid;

    public CommonOpOnly(TypeAttr.NamedType namedType, boolean realVoid) {
        this.typeName = namedType.typeName(TypeAttr.NameType.RAW);
        this.namedType = namedType;
        this.realVoid = realVoid;
    }

    @Override
    public FuncOperation getFuncOperation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MemoryOperation getMemoryOperation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public String makeOperation() {
                return realVoid ? CommonOperation.makeVoidOperation() : CommonOperation.makeStaticOperation(typeName);
            }

            @Override
            public UpperType getUpperType() {
                // use Ptr<?> instead of Ptr<? extends Void>
                return new End(realVoid ? CommonTypes.BindTypes.Ptr : namedType);
            }
        };
    }
}
