package generator.types.operations;

import static generator.TypeNames.MEM_GET;
import static generator.TypeNames.MEM_SET;

public class CommonValueBased implements OperationAttr.ValueBasedOperation {
    private final String name;
    private final String memoryLayout;

    public CommonValueBased(String typeName, String memoryLayout) {
        this.name = typeName;
        this.memoryLayout = memoryLayout;
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
        };
    }

    @Override
    public CopyOperation getMemoryOperation() {
        return new CopyOperation() {
            @Override
            public String copyFromMS(String ms, long offset) {
                return MEM_GET.formatted(memoryLayout, offset);
            }

            @Override
            public String copyToMS(String ms, long offset, String varName) {
                return MEM_SET.formatted(memoryLayout, offset, varName);
            }
        };
    }
}
