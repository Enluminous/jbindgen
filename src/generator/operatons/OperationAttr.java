package generator.operatons;

public class OperationAttr {
    public sealed interface Operation {
        FuncOperation getFuncOperation();
    }


    public non-sealed interface ValueBasedOperation extends Operation {

    }

    public non-sealed interface MemoryBasedOperation extends Operation {

    }

}
