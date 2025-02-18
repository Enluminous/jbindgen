package generator.types.operations;

import generator.types.CommonTypes;
import generator.types.TypeAttr;

public class CommonOpOnly<T extends TypeAttr.NamedType & TypeAttr.TypeRefer> implements OperationAttr.CommonOnlyOperation {
    private final String typeName;
    private final T type;
    private final boolean realVoid;

    public CommonOpOnly(T type, boolean realVoid) {
        this.typeName = type.typeName(TypeAttr.NameType.RAW);
        this.type = type;
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
            public Operation makeOperation() {
                return realVoid ? CommonOperation.makeVoidOperation() : CommonOperation.makeStaticOperation(type, typeName);
            }

            @Override
            public UpperType getUpperType() {
                // use Ptr<?> instead of Ptr<? extends Void>
                return new End<>(realVoid ? CommonTypes.BindTypes.Ptr : type);
            }
        };
    }
}
