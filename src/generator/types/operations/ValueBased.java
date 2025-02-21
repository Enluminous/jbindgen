package generator.types.operations;

import generator.types.CommonTypes;
import generator.types.TypeAttr;
import generator.types.TypeImports;
import generator.types.ValueBasedType;

public class ValueBased<T extends TypeAttr.NamedType & TypeAttr.TypeRefer & TypeAttr.OperationType> implements OperationAttr.ValueBasedOperation {
    private final T type;
    private final String typeName;
    private final CommonTypes.Primitives primitives;
    private final CommonTypes.BindTypes bindTypes;

    public ValueBased(T type, String typeName, CommonTypes.BindTypes bindTypes) {
        this.type = type;
        this.typeName = typeName;
        this.primitives = bindTypes.getPrimitiveType();
        this.bindTypes = bindTypes;
    }

    @Override
    public FuncOperation getFuncOperation() {
        return new FuncOperation() {
            @Override
            public Result destructToPara(String varName) {
                return new Result(varName + ".operator().value()", new TypeImports().addUseImports(type));
            }

            @Override
            public Result constructFromRet(String varName) {
                return new Result("new " + typeName + "(" + varName + ")", new TypeImports().addUseImports(type));
            }

            @Override
            public CommonTypes.Primitives getPrimitiveType() {
                return primitives;
            }
        };
    }

    @Override
    public MemoryOperation getMemoryOperation() {
        return new MemoryOperation() {
            @Override
            public Getter getter(String ms, long offset) {
                return new Getter("", typeName, "new %s(%s)".formatted(typeName,
                        "MemoryUtils.get%s(%s, %s)".formatted(primitives.getMemoryUtilName(), ms, offset)), new TypeImports().addUseImports(type));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                CommonOperation.UpperType upperType = getCommonOperation().getUpperType();
                return new Setter(upperType.typeName(TypeAttr.NameType.WILDCARD) + " " + varName,
                        "MemoryUtils.set%s(%s, %s, %s.operator().value())".formatted(primitives.getMemoryUtilName(), ms, offset, varName), upperType.typeImports());
            }
        };
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public Operation makeOperation() {
                return CommonOperation.makeStaticOperation(type, typeName);
            }

            @Override
            public UpperType getUpperType() {
                if (type instanceof CommonTypes.BindTypes) {
                    return new Reject<>(type);
                }
                End<?> end = new End<>(type);
                if (type instanceof ValueBasedType v && v.getPointerType().isPresent()) {
//                    end = new End(((TypeAttr.NamedType) v.getPointerType().get().pointee()));
                    // consider use value based name
                    return end;
                }
                return new Warp<>(bindTypes.getOperations().getValue(), end);
            }
        };
    }

}
