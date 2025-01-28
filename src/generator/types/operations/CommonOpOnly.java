package generator.types.operations;

public class CommonOpOnly implements OperationAttr.NoneBasedOperation {
    private final String typeName;
    private final boolean inline;

    public CommonOpOnly(String typeName, boolean inline) {
        this.typeName = typeName;
        this.inline = inline;
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
                return inline ? CommonOperation.makeVoidOperation() : CommonOperation.makeStaticOperation(typeName);
            }
        };
    }
}
