package generator.types;

import generator.types.operations.ArrayNamedOp;
import generator.types.operations.OperationAttr;

import static utils.CommonUtils.Assert;

public record ArrayTypeNamed(String typeName, long length, TypeAttr.TypeRefer element, long byteSize) implements
        TypeAttr.SizedType, TypeAttr.OperationType, TypeAttr.NamedType, TypeAttr.TypeRefer, TypeAttr.GenerationType {
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
    public MemoryLayouts getMemoryLayout() {
        return AbstractGenerationType.makeMemoryLayout(byteSize);
    }
}
