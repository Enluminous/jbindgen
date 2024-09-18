package generator.types.operations;

import generator.types.CommonTypes;

public interface FuncOperation {
    /**
     * func(Type.destruct())
     */
    String destructToPara(String varName);

    /**
     * var type = construct(func());
     */
    String constructFromRet(String varName);


    CommonTypes.Primitives getPrimitiveType();
}
