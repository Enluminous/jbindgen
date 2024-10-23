package generator.types.operations;

import generator.types.CommonTypes;
import generator.types.TypeAttr;

import java.util.ArrayList;

import static generator.TypeNames.MEM_GET;
import static generator.TypeNames.MEM_SET;

public class FunctionPtrBased implements OperationAttr.ValueBasedOperation {

    private final String name;

    public FunctionPtrBased(String name) {
        this.name = name;
    }

    @Override
    public FuncOperation getFuncOperation() {
        return new FuncOperation() {
            @Override
            public String destructToPara(String varName) {
                return varName + ".pointer()";
            }

            @Override
            public String constructFromRet(String varName) {
                return "new " + name + "(" + varName + ")";
            }

            @Override
            public CommonTypes.Primitives getPrimitiveType() {
                return CommonTypes.Primitives.ADDRESS;
            }
        };
    }

    @Override
    public CopyOperation getMemoryOperation() {
        return new CopyOperation() {
            @Override
            public String copyFromMS(String ms, long offset) {
                return MEM_GET.formatted(CommonTypes.Primitives.ADDRESS.getMemoryLayout(), offset);
            }

            @Override
            public String copyToMS(String ms, long offset, String varName) {
                return MEM_SET.formatted(CommonTypes.Primitives.ADDRESS.getMemoryLayout(), offset, varName);
            }
        };
    }
}
