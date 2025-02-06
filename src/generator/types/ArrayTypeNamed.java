package generator.types;

import generator.Utils;
import generator.types.operations.ArrayNamedOp;
import generator.types.operations.OperationAttr;

import static utils.CommonUtils.Assert;

public record ArrayTypeNamed(String typeName, long length, TypeAttr.TypeRefer element, long byteSize) implements
        TypeAttr.SizedType, TypeAttr.OperationType, TypeAttr.NamedType, TypeAttr.TypeRefer, TypeAttr.GenerationType {
    public static final CommonTypes.SpecificTypes LIST_TYPE = CommonTypes.SpecificTypes.Array;

    public ArrayTypeNamed {
        Assert(length > 0, "length must be greater than zero");
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new ArrayNamedOp(typeName, this);
    }

    @Override
    public TypeImports getDefineImportTypes() {
        return element.getUseImportTypes()
                .addUseImports(LIST_TYPE)
                .addUseImports(CommonTypes.BasicOperations.Operation)
                .addUseImports(CommonTypes.BasicOperations.Info)
                .addUseImports(CommonTypes.BindTypes.Ptr);
    }

    @Override
    public TypeImports getUseImportTypes() {
        return new TypeImports(this);
    }

    @Override
    public String typeName(TypeAttr.NameType nameType) {
        return typeName;
    }

    @Override
    public String getMemoryLayout() {
        return Utils.makeMemoryLayout(byteSize);
    }

    @Override
    public long getByteSize() {
        return byteSize;
    }
}
