package generator.types.operations;

import generator.types.CommonTypes;
import generator.types.TypeImports;

public interface FuncOperation {
    record Result(String codeSegment, TypeImports imports) {

    }

    /**
     * func(Type.destruct())
     */
    Result destructToPara(String varName);

    /**
     * var type = construct(func());
     */
    Result constructFromRet(String varName);


    CommonTypes.Primitives getPrimitiveType();
}
