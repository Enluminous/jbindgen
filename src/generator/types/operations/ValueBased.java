package generator.types.operations;

import generator.types.CommonTypes;
import generator.types.TypeAttr;
import generator.types.ValueBasedType;

public class ValueBased implements OperationAttr.ValueBasedOperation {
    private final TypeAttr.NamedType namedType;
    private final String typeName;
    private final CommonTypes.Primitives primitives;
    private final CommonTypes.BindTypes bindTypes;

    public ValueBased(TypeAttr.NamedType namedType, String typeName, CommonTypes.BindTypes bindTypes) {
        this.namedType = namedType;
        this.typeName = typeName;
        this.primitives = bindTypes.getPrimitiveType();
        this.bindTypes = bindTypes;
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
                        "MemoryUtils.get%s(%s, %s)".formatted(primitives.getMemoryUtilName(), ms, offset)));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                String typeName = getCommonOperation().getUpperType().typeName(TypeAttr.NameType.WILDCARD);
                return new Setter(typeName + " " + varName,
                        "MemoryUtils.set%s(%s, %s, %s.operator().value())".formatted(primitives.getMemoryUtilName(), ms, offset, varName));
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
                End end = new End(namedType);
                if (namedType instanceof ValueBasedType v && v.getPointerType().isPresent()) {
//                    end = new End(((TypeAttr.NamedType) v.getPointerType().get().pointee()));
                    // consider use value based name
                    return end;
                }
                return new Warp(bindTypes.getOperations().getValue(), end);
            }
        };
    }

}
