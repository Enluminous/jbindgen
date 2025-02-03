package generator.types.operations;

import generator.generation.generator.FuncProtocolGenerator;
import generator.types.CommonTypes;
import generator.types.FunctionPtrType;
import generator.types.TypeAttr;

public class FunctionPtrBased implements OperationAttr.ValueBasedOperation {

    private final FunctionPtrType functionPtrType;
    private final String typeName;

    public FunctionPtrBased(FunctionPtrType functionPtrType, String typeName) {
        this.functionPtrType = functionPtrType;
        this.typeName = typeName;
    }

    @Override
    public FuncOperation getFuncOperation() {
        return new FuncOperation() {
            @Override
            public String destructToPara(String varName) {
                return varName + ".operator().value()";
            }

            @Override
            public String constructFromRet(String varName) {
                return "new " + typeName + "(" + varName + ")";
            }

            @Override
            public CommonTypes.Primitives getPrimitiveType() {
                return CommonTypes.Primitives.ADDRESS;
            }
        };
    }

    @Override
    public MemoryOperation getMemoryOperation() {
        return new MemoryOperation() {
            @Override
            public Getter getter(String ms, long offset) {
                return new Getter("", typeName, "new %s(%s)".formatted(typeName,
                        "MemoryUtils.getAddr(%s, %s)".formatted(ms, offset)));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                return new Setter(typeName + " " + varName,
                        "MemoryUtils.setAddr(%s, %s, %s.operator().value())".formatted(ms, offset, varName));
            }
        };
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public String makeOperation() {
                return CommonOperation.makeStaticOperation(typeName);
            }

            @Override
            public UpperType getUpperType() {
                End end = new End(functionPtrType, functionPtrType.typeName(TypeAttr.NameType.RAW) + "." + FuncProtocolGenerator.FUNCTION_TYPE_NAME);
                return new Warp(CommonTypes.ValueInterface.PtrI, end);
            }
        };
    }
}
