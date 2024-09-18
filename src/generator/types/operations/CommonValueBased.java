package generator.types.operations;

import generator.types.CommonTypes;

import static generator.TypeNames.MEM_GET;
import static generator.TypeNames.MEM_SET;

public class CommonValueBased implements OperationAttr.ValueBasedOperation {
    private final String name;
    private final CommonTypes.Primitives primitives;

    public CommonValueBased(String typeName, CommonTypes.Primitives primitives) {
        this.name = typeName;
        this.primitives = primitives;
    }

    @Override
    public FuncOperation getFuncOperation() {
        return new FuncOperation() {
            @Override
            public String destructToPara(String varName) {
                return varName + ".value()";
            }

            @Override
            public String constructFromRet(String varName) {
                return "new " + name + "(" + varName + ")";
            }

            @Override
            public CommonTypes.Primitives getPrimitiveType() {
                return primitives;
            }
        };
    }

    @Override
    public CopyOperation getMemoryOperation() {
        return new CopyOperation() {
            @Override
            public String copyFromMS(String ms, long offset) {
                return MEM_GET.formatted(primitives.getMemoryLayout(), offset);
            }

            @Override
            public String copyToMS(String ms, long offset, String varName) {
                return MEM_SET.formatted(primitives.getMemoryLayout(), offset, varName);
            }
        };
    }
}
