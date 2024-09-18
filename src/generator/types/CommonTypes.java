package generator.types;

import generator.types.operations.CommonValueBased;
import generator.types.operations.OperationAttr;

import java.lang.foreign.ValueLayout;
import java.util.HashSet;
import java.util.Set;

public class CommonTypes {
    public enum Primitives implements TypeAttr.BaseType, TypeAttr.Type {
        JAVA_BOOLEAN("ValueLayout.JAVA_BOOLEAN", "ValueLayout.OfBoolean", "boolean", "Boolean", ValueLayout.JAVA_BOOLEAN.byteSize()),
        JAVA_BYTE("ValueLayout.JAVA_BYTE", "ValueLayout.OfBye", "byte", "Byte", ValueLayout.JAVA_BYTE.byteSize()),
        JAVA_SHORT("ValueLayout.JAVA_SHORT", "ValueLayout.OfShort", "short", "Short", ValueLayout.JAVA_SHORT.byteSize()),
        JAVA_CHAR("ValueLayout.JAVA_CHAR", "ValueLayout.OfChar", "char", "Character", ValueLayout.JAVA_CHAR.byteSize()),
        JAVA_INT("ValueLayout.JAVA_INT", "ValueLayout.OfInt", "int", "Integer", ValueLayout.JAVA_INT.byteSize()),
        JAVA_LONG("ValueLayout.JAVA_LONG", "ValueLayout.OfLong", "long", "Long", ValueLayout.JAVA_LONG.byteSize()),
        JAVA_FLOAT("ValueLayout.JAVA_FLOAT", "ValueLayout.OfFloat", "float", "Float", ValueLayout.JAVA_FLOAT.byteSize()),
        JAVA_DOUBLE("ValueLayout.JAVA_DOUBLE", "ValueLayout.OfDouble", "double", "Double", ValueLayout.JAVA_DOUBLE.byteSize()),
        ADDRESS("AddressLayout.ADDRESS", "AddressLayout", "MemorySegment", "MemorySegment", ValueLayout.ADDRESS.byteSize()),
        FLOAT16("ValueLayout.JAVA_SHORT", "ValueLayout.OfFloat", null, null, ValueLayout.JAVA_SHORT.byteSize()),
        LONG_DOUBLE("MemoryLayout.structLayout(ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG)", "MemoryLayout", null, null, ValueLayout.JAVA_LONG.byteSize() * 2),
        Integer128("MemoryLayout.structLayout(ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG)", "MemoryLayout", null, null, ValueLayout.JAVA_LONG.byteSize() * 2);

        private final String memoryLayout;
        private final String typeName;
        private final String primitiveTypeName;
        private final String boxedTypeName;
        private final long byteSize;

        Primitives(String memoryLayout, String typeName, String primitiveTypeName, String boxedTypeName, long byteSize) {
            this.memoryLayout = memoryLayout;
            this.typeName = typeName;
            this.primitiveTypeName = primitiveTypeName;
            this.boxedTypeName = boxedTypeName;
            this.byteSize = byteSize;
        }

        public String getTypeName() {
            return typeName;
        }

        @Override
        public Set<TypeAttr.Type> getReferencedTypes() {
            return Set.of();
        }

        public String getMemoryLayout() {
            return memoryLayout;
        }

        public long getByteSize() {
            return byteSize;
        }

        public String getPrimitiveTypeName() {
            return primitiveTypeName;
        }

        public String getBoxedTypeName() {
            return boxedTypeName;
        }
    }

    public enum BindTypes implements TypeAttr.BaseType, TypeAttr.ValueBased, TypeAttr.NormalType {
        I8("I8<%s>", Primitives.JAVA_BYTE),
        I16("I16<%s>", Primitives.JAVA_SHORT),
        I32("I32<%s>", Primitives.JAVA_INT),
        I64("I64<%s>", Primitives.JAVA_LONG),
        FP32("FP32<%s>", Primitives.JAVA_FLOAT),
        FP64("FP64<%s>", Primitives.JAVA_DOUBLE),
        Pointer("Pointer<%s>", Primitives.ADDRESS),
        FP16("FP16<%s>", Primitives.FLOAT16),
        FP128("FP128<%s>", Primitives.LONG_DOUBLE),
        I128("I128<%s>", Primitives.Integer128);
        private final String type;
        private final Primitives primitives;

        BindTypes(String type, Primitives primitives) {
            this.type = type;
            this.primitives = primitives;
        }

        @Override
        public String getTypeName() {
            return type;
        }

        @Override
        public Set<TypeAttr.Type> getReferencedTypes() {
            Set<TypeAttr.Type> types = new HashSet<>();
            types.add(primitives);
            types.addAll(primitives.getReferencedTypes());
            return Set.copyOf(types);
        }

        @Override
        public OperationAttr.Operation getOperation() {
            return new CommonValueBased(type, primitives);
        }

        @Override
        public String getMemoryLayout() {
            return primitives.getMemoryLayout();
        }

        @Override
        public long getByteSize() {
            return primitives.getByteSize();
        }

        public Primitives getPrimitiveType() {
            return primitives;
        }
    }

    public static final class ListType implements TypeAttr.BaseType, TypeAttr.NormalType {
        public static ListType makeNListType(String typeName, TypeAttr.NormalType innerType) {
            return new ListType("NList<" + typeName + ">", innerType);
        }

        public static ListType makeValueListType(BindTypes bindTypes, String typeName) {
            return new ListType(bindTypes.getTypeName().formatted("?") + "List", bindTypes);
        }

        private final String type;
        private final TypeAttr.NormalType normalType;

        private ListType(String type, TypeAttr.NormalType normalType) {
            this.type = type;
            this.normalType = normalType;
        }

        @Override
        public String getTypeName() {
            return type;
        }

        @Override
        public Set<TypeAttr.Type> getReferencedTypes() {
            Set<TypeAttr.Type> types = new HashSet<>();
            types.add(normalType);
            types.addAll(normalType.getReferencedTypes());
            return Set.copyOf(types);
        }

        @Override
        public OperationAttr.Operation getOperation() {
            throw new UnsupportedOperationException("todo this");
        }

        @Override
        public String getMemoryLayout() {
            return Primitives.ADDRESS.getMemoryLayout();
        }

        @Override
        public long getByteSize() {
            return Primitives.ADDRESS.getByteSize();
        }

        public boolean isValueBased() {
            return TypeAttr.isValueBased(normalType);
        }
    }
}