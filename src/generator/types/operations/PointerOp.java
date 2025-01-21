package generator.types.operations;

import generator.types.CommonTypes;

import static generator.TypeNames.MEM_GET;
import static generator.TypeNames.MEM_SET;

public class PointerOp implements OperationAttr.ValueBasedOperation {

    private final String typeName;
    private final String pointeeName;

    public PointerOp(String typeName, String pointeeName) {
        this.typeName = typeName;
        this.pointeeName = pointeeName;
    }

    @Override
    public FuncOperation getFuncOperation() {
        return new FuncOperation() {
            @Override
            public String destructToPara(String varName) {
                return varName + ".pointer().getMemorySegment()";
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
                        MEM_GET.formatted(ms, CommonTypes.Primitives.ADDRESS.getMemoryLayout(), offset)));
            }

            @Override
            public Setter setter(String ms, long offset, String varName) {
                return new Setter(CommonTypes.BindTypes.makePtrWildcardName(pointeeName) + " " + varName,
                        MEM_SET.formatted(ms, CommonTypes.Primitives.ADDRESS.getMemoryLayout(), offset, varName + ".value()"));
            }
        };
    }

    @Override
    public CommonOperation getCommonOperation() {
        return new CommonOperation() {
            @Override
            public String getTypeName() {
                return typeName;
            }
        };
    }
}
