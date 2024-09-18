package generator.types;

import generator.types.operations.CommonValueBased;
import generator.types.operations.OperationAttr;

import java.lang.foreign.ValueLayout;
import java.util.Set;

public enum Primitives implements TypeAttr.NormalType, TypeAttr.ValueBased {
    Boolean("boolean", "ValueLayout.JAVA_BOOLEAN", "Boolean", "I8<?>", "I8", false, ValueLayout.JAVA_BOOLEAN.byteSize()),
    Byte("byte", "ValueLayout.JAVA_BYTE", "Byte", "I8<?>", "I8", true, ValueLayout.JAVA_BYTE.byteSize()),
    Short("short", "ValueLayout.JAVA_SHORT", "Short", "I16<?>", "I16", true, ValueLayout.JAVA_SHORT.byteSize()),
    Char("char", "ValueLayout.JAVA_CHAR", "Character", "I16<?>", "I16", false, ValueLayout.JAVA_CHAR.byteSize()),
    Integer("int", "ValueLayout.JAVA_INT", "Integer", "I32<?>", "I32", true, ValueLayout.JAVA_INT.byteSize()),
    Float("float", "ValueLayout.JAVA_FLOAT", "Float", "FP32<?>", "FP32", true, ValueLayout.JAVA_FLOAT.byteSize()),
    Double("double", "ValueLayout.JAVA_DOUBLE", "Double", "FP64<?>", "FP64", true, ValueLayout.JAVA_DOUBLE.byteSize()),
    Address("MemorySegment", "ValueLayout.ADDRESS", "MemorySegment", "Pointer<?>", "Pointer", true, ValueLayout.ADDRESS.byteSize()),
    Float16("short", "ValueLayout.JAVA_SHORT", "Float16", "FP16<?>", "FP16", false, ValueLayout.JAVA_SHORT.byteSize()),
    // extend part
    Integer128("Integer128", "MemoryLayout.structLayout(ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG)", "Integer128", "I128<?>", "I128", false, ValueLayout.JAVA_LONG.byteSize() * 2),
    LongDouble("LongDouble", "MemoryLayout.structLayout(ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG)", "LongDouble", "FP128<?>", "FP128", false, ValueLayout.JAVA_DOUBLE.byteSize() * 2);

    private final String primitiveName;
    private final String memoryLayout;
    private final String wrapperName;
    private final String bindName;
    private final String typeName;
    private final boolean enable;
    private final long byteSize;

    Primitives(String primitiveName, String memoryLayout, String wrapperName, String bindName, String typeName, boolean enable, long byteSize) {
        this.primitiveName = primitiveName;
        this.memoryLayout = memoryLayout;
        this.wrapperName = wrapperName;
        this.bindName = bindName;
        this.typeName = typeName;
        this.enable = enable;
        this.byteSize = byteSize;
    }

    @Override
    public long getByteSize() {
        return byteSize;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new CommonValueBased(bindName, memoryLayout);
    }

    @Override
    public String getMemoryLayout() {
        return memoryLayout;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    public String getWrapperName() {
        return wrapperName;
    }

    public String getBindName() {
        return bindName;
    }

    public boolean isEnable() {
        return enable;
    }

    @Override
    public Primitives getNonWrappedType() {
        return this;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        // todo we need decl the common type and ffm type
        return null;
    }
}