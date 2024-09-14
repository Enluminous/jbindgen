package generator.operatons;

public interface FuncOperation {
    /**
     * func(Type.destruct())
     */
    interface DestructInFun {
        String destruct();
    }

    /**
     * var type = construct(func());
     */
    interface ConstructInFun {
        String construct(String varName);
    }
}
