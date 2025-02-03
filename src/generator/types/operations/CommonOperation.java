package generator.types.operations;

import generator.types.CommonTypes;
import generator.types.TypeAttr;

public interface CommonOperation {
    String makeOperation();

//    TypeAttr.OperationType getUpperType();

    static String makeStaticOperation(String typeName) {
        return typeName + ".OPERATIONS";
    }

    static String makeVoidOperation() {
        return CommonTypes.BasicOperations.Info.typeName(TypeAttr.NameType.RAW) + ".makeOperations()";
    }
}
