package generator.operatons;

public interface FuncOperation {
    /**
     * func(Type.destruct())
     */
    String destructToPara(String varName);

    /**
     * var type = construct(func());
     */
    String constructFromRet(String varName);
}
