package generator.types.operations;

import generator.types.ArrayType;
import generator.types.CommonTypes;
import generator.types.TypeAttr;

import java.util.HashSet;
import java.util.Set;

import static generator.generation.generator.CommonGenerator.ARRAY_MAKE_OPERATION_METHOD;

public class ArrayOp implements OperationAttr.MemoryBasedOperation {
    private final String typeName;
    private final ArrayType arrayType;
    private final TypeAttr.OperationType element;

    public ArrayOp(String typeName, ArrayType arrayType) {
        this.arrayType = arrayType;
        this.element = (TypeAttr.OperationType) arrayType.element();
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
                return "new %s(%s, %s)".formatted(typeName, varName, element.getOperation().getCommonOperation().makeOperation().str());
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
                return new Getter("", typeName, "new %s(%s, %s)".formatted(typeName,
                        "%s.asSlice(%s, %s)".formatted(ms, offset, arrayType.byteSize()),
                        element.getOperation().getCommonOperation().makeOperation().str()));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                String typeName = getCommonOperation().getUpperType().typeName(TypeAttr.NameType.WILDCARD);
                return new Setter(typeName + " " + varName,
                        "MemoryUtils.memcpy(%s, %s, %s.operator().value(), 0, %s)".formatted(ms, offset, varName, arrayType.byteSize()));

            }
        };
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public Operation makeOperation() {
                Operation eleOp = element.getOperation().getCommonOperation().makeOperation();
                var ref = new HashSet<>(eleOp.typeRefers());
                ref.add(arrayType);
                return new Operation(arrayType.typeName(TypeAttr.NameType.RAW) + "." + ARRAY_MAKE_OPERATION_METHOD + "(%s, %s)"
                        .formatted(eleOp.str(), arrayType.byteSize()), ref);
            }

            @Override
            public UpperType getUpperType() {
                return new Warp(CommonTypes.BasicOperations.ArrayI, element.getOperation().getCommonOperation());
            }
        };
    }

}
