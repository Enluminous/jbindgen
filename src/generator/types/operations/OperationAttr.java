package generator.types.operations;

public class OperationAttr {
    public sealed interface Operation {
        FuncOperation getFuncOperation();

        CopyOperation getMemoryOperation();

        CommonOperation getCommonOperation();
    }


    /**
     * the type is value based
     */
    public non-sealed interface ValueBasedOperation extends Operation {
    }

    /**
     * the type is MemorySegment stored
     */
    public non-sealed interface MemoryBasedOperation extends Operation {

    }
}
