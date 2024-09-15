package generator.constants;

import generator.types.TypeAttr;
import generator.types.operations.OperationAttr;

public class ConstValue {
    private final TypeAttr.NormalType type;

    private final WhenConstruct construct;

    public interface WhenConstruct {

        String construct(OperationAttr.Operation op);
    }

    public ConstValue(TypeAttr.NormalType type, WhenConstruct construct) {
        this.type = type;
        this.construct = construct;
    }
}
