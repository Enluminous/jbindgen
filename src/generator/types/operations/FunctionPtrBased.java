package generator.types.operations;

import generator.generation.generator.FuncProtocolGenerator;
import generator.types.*;

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
            public Result destructToPara(String varName) {
                return new Result(varName + ".operator().value()", new TypeImports().addUseImports(functionPtrType));
            }

            @Override
            public Result constructFromRet(String varName) {
                return new Result("new " + typeName + "(" + varName + ")", new TypeImports().addUseImports(functionPtrType));
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
                        "MemoryUtils.getAddr(%s, %s)".formatted(ms, offset)), new TypeImports().addUseImports(functionPtrType));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                CommonOperation.UpperType upperType = getCommonOperation().getUpperType();
                return new Setter(upperType.typeName(TypeAttr.NameType.WILDCARD) + " " + varName,
                        "MemoryUtils.setAddr(%s, %s, %s.operator().value())".formatted(ms, offset, varName), upperType.typeImports());
            }
        };
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public Operation makeOperation() {
                return CommonOperation.makeStaticOperation(functionPtrType, typeName);
            }


            @Override
            public MemoryLayouts makeDirectMemoryLayout() {
                return CommonOperation.makeStaticMemoryLayout(CommonTypes.Primitives.ADDRESS.getMemoryLayout());
            }

            @Override
            public UpperType getUpperType() {
                End<?> end = new End<>(functionPtrType, functionPtrType.typeName(TypeAttr.NameType.RAW) + "." + FuncProtocolGenerator.FUNCTION_TYPE_NAME);
                return new Warp<>(CommonTypes.ValueInterface.PtrI, end);
            }
        };
    }
}
