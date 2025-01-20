package generator.types;

import generator.types.operations.OperationAttr;
import generator.types.operations.ValueBased;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandles;
import java.util.*;

public class CommonTypes {
    public enum Primitives {
        JAVA_BOOLEAN("AddressLayout.JAVA_BOOLEAN", "AddressLayout.OfBoolean", "boolean", "Boolean", null, AddressLayout.JAVA_BOOLEAN.byteSize()),
        JAVA_BYTE("AddressLayout.JAVA_BYTE", "AddressLayout.OfBye", "byte", "Byte", null, AddressLayout.JAVA_BYTE.byteSize()),
        JAVA_SHORT("AddressLayout.JAVA_SHORT", "AddressLayout.OfShort", "short", "Short", null, AddressLayout.JAVA_SHORT.byteSize()),
        JAVA_CHAR("AddressLayout.JAVA_CHAR", "AddressLayout.OfChar", "char", "Character", null, AddressLayout.JAVA_CHAR.byteSize()),
        JAVA_INT("AddressLayout.JAVA_INT", "AddressLayout.OfInt", "int", "Integer", null, AddressLayout.JAVA_INT.byteSize()),
        JAVA_LONG("AddressLayout.JAVA_LONG", "AddressLayout.OfLong", "long", "Long", null, AddressLayout.JAVA_LONG.byteSize()),
        JAVA_FLOAT("AddressLayout.JAVA_FLOAT", "AddressLayout.OfFloat", "float", "Float", null, AddressLayout.JAVA_FLOAT.byteSize()),
        JAVA_DOUBLE("AddressLayout.JAVA_DOUBLE", "AddressLayout.OfDouble", "double", "Double", null, AddressLayout.JAVA_DOUBLE.byteSize()),
        ADDRESS("AddressLayout.ADDRESS", "AddressLayout", "MemorySegment", "MemorySegment", FFMTypes.MEMORY_SEGMENT, AddressLayout.ADDRESS.byteSize()),
        FLOAT16("AddressLayout.JAVA_SHORT", "AddressLayout.OfFloat", null, null, null, AddressLayout.JAVA_SHORT.byteSize()),
        LONG_DOUBLE("MemoryLayout.structLayout(AddressLayout.JAVA_LONG, AddressLayout.JAVA_LONG)", "MemoryLayout", null, null, null, AddressLayout.JAVA_LONG.byteSize() * 2),
        Integer128("MemoryLayout.structLayout(AddressLayout.JAVA_LONG, AddressLayout.JAVA_LONG)", "MemoryLayout", null, null, null, AddressLayout.JAVA_LONG.byteSize() * 2);

        private final String memoryLayout;
        private final String typeName;
        private final String primitiveTypeName;
        private final String boxedTypeName;
        private final FFMTypes ffmType;
        private final long byteSize;

        Primitives(String memoryLayout, String typeName, String primitiveTypeName, String boxedTypeName, FFMTypes ffmType, long byteSize) {
            this.memoryLayout = memoryLayout;
            this.typeName = typeName;
            this.primitiveTypeName = primitiveTypeName;
            this.boxedTypeName = boxedTypeName;
            this.ffmType = ffmType;
            this.byteSize = byteSize;
        }

        public String getTypeName() {
            return typeName;
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

        public FFMTypes getFfmType() {
            return ffmType;
        }
    }

    public enum ValueInterface implements BaseType {
        I8("ValueI8", Primitives.JAVA_BYTE),
        I16("ValueI16", Primitives.JAVA_SHORT),
        I32("ValueI32", Primitives.JAVA_INT),
        I64("ValueI64", Primitives.JAVA_LONG),
        FP32("ValueFP32", Primitives.JAVA_FLOAT),
        FP64("ValueFP64", Primitives.JAVA_DOUBLE),
        Pointer("ValuePointer", Primitives.ADDRESS),
        FP16("ValueFP16", Primitives.FLOAT16),
        FP128("ValueFP128", Primitives.LONG_DOUBLE),
        I128("ValueI128", Primitives.Integer128);

        private final String typeName;
        private final Primitives primitive;

        ValueInterface(String typeName, Primitives primitive) {
            this.typeName = typeName;
            this.primitive = primitive;
        }

        @Override
        public Set<TypeAttr.ReferenceType> getUseImportTypes() {
            return Set.of(this);
        }

        @Override
        public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
            return Set.of();
        }

        @Override
        public Set<TypeAttr.GenerationType> toGenerationTypes() {
            return Set.of(this);
        }

        public String getTypeName() {
            return typeName;
        }

        public Primitives getPrimitive() {
            return primitive;
        }
    }

    public enum BindTypes implements
            BaseType,
            TypeAttr.SizedType,
            TypeAttr.OperationType,
            TypeAttr.NamedType,
            TypeAttr.ReferenceType,
            TypeAttr.GenerationType {
        I8("BasicI8", ValueInterface.I8),
        I16("BasicI16", ValueInterface.I16),
        I32("BasicI32", ValueInterface.I32),
        I64("BasicI64", ValueInterface.I64),
        FP32("BasicFP32", ValueInterface.FP32),
        FP64("BasicFP64", ValueInterface.FP64),
        Pointer("BasicPointer", ValueInterface.Pointer),
        FP16("BasicFP16", ValueInterface.FP16),
        FP128("BasicFP128", ValueInterface.FP128),
        I128("BasicI128", ValueInterface.I128);
        private final String rawName;
        private final ValueInterface valueInterface;

        BindTypes(String rawName, ValueInterface primitives) {
            this.rawName = rawName;
            this.valueInterface = primitives;
        }

        public static String makePtrGenericName(String t) {
            return Pointer.rawName + "<%s>".formatted(t);
        }

        public static String makePtrWildcardName(String t) {
            return Pointer.rawName + "<? extends %s>".formatted(t);
        }

        @Override
        public Set<TypeAttr.ReferenceType> getUseImportTypes() {
            return Set.of(this);
        }

        @Override
        public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
            return valueInterface.getUseImportTypes();
        }

        public ValueInterface getValueInterface() {
            return valueInterface;
        }

        @Override
        public Set<TypeAttr.GenerationType> toGenerationTypes() {
            return Set.of(this);
        }

        public OperationAttr.Operation getOperation() {
            return new ValueBased(rawName, valueInterface.primitive);
        }

        public String getRawName() {
            return rawName;
        }


        public ListTypes getListType() {
            return Arrays.stream(ListTypes.values()).filter(listTypes -> listTypes.elementType.equals(this)).findFirst().orElseThrow();
        }

        @Override
        public String getMemoryLayout() {
            return valueInterface.primitive.getMemoryLayout();
        }

        @Override
        public long getByteSize() {
            return valueInterface.primitive.getByteSize();
        }

        @Override
        public String typeName() {
            return rawName;
        }

        public Primitives getPrimitiveType() {
            return valueInterface.getPrimitive();
        }
    }

    public enum ListTypes implements BaseType {
        I8List("I8List", BindTypes.I8),
        I16List("I16List", BindTypes.I16),
        I32List("I32List", BindTypes.I32),
        I64List("I64List", BindTypes.I64),
        FP32List("FP32List", BindTypes.FP32),
        FP64List("FP64List", BindTypes.FP64),
        PointerList("PointerList", BindTypes.Pointer),
        FP16List("FP16List", BindTypes.FP16),
        FP128List("FP128List", BindTypes.FP128),
        I128List("I128List", BindTypes.I128);
        private final String rawName;
        private final BindTypes elementType;

        ListTypes(String rawName, BindTypes elementType) {
            this.rawName = rawName;
            this.elementType = elementType;
        }

        @Override
        public Set<TypeAttr.ReferenceType> getUseImportTypes() {
            return Set.of(this);
        }

        @Override
        public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
            return elementType.getUseImportTypes();
        }

        @Override
        public Set<TypeAttr.GenerationType> toGenerationTypes() {
            return Set.of(this);
        }

        public BindTypes getElementType() {
            return elementType;
        }

        public String getGenericName(String t) {
            return rawName + "<%s>".formatted(t);
        }

        public String getRawName() {
            return rawName;
        }
    }

    public enum SpecificTypes implements BaseType {
        AbstractNativeList(true, Set.of()),
        SymbolProvider(false, Set.of()),
        Utils(false, Set.of()),
        NList(true, Set.of(AbstractNativeList)),
        NString(false, Set.of(NList)),
        Array(true, Set.of(AbstractNativeList));

        final boolean generic;
        private final Set<TypeAttr.ReferenceType> referenceTypes;

        SpecificTypes(boolean generic, Set<TypeAttr.ReferenceType> referenceTypes) {
            this.generic = generic;
            this.referenceTypes = referenceTypes;
        }

        @Override
        public Set<TypeAttr.ReferenceType> getUseImportTypes() {
            return Set.of(this);
        }

        @Override
        public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
            HashSet<TypeAttr.ReferenceType> set = new HashSet<>(referenceTypes);
            set.add(this);
            return set;
        }

        @Override
        public Set<TypeAttr.GenerationType> toGenerationTypes() {
            return Set.of(this);
        }

        public String getGenericName(String t) {
            if (!generic) {
                throw new IllegalStateException("Cannot get generic name for non-generic type");
            }
            return name() + "<%s>".formatted(t);
        }

        public String getWildcardName(String t) {
            if (!generic) {
                throw new IllegalStateException("Cannot get wildcard name for non-generic type");
            }
            return name() + "<? extends %s>".formatted(t);
        }
    }


    public enum FFMTypes implements BaseType {
        MEMORY_SEGMENT(MemorySegment.class),
        MEMORY_LAYOUT(MemoryLayout.class),
        ADDRESS_LAYOUT(AddressLayout.class),
        ARENA(Arena.class),
        METHOD_HANDLES(MethodHandles.class),
        FUNCTION_DESCRIPTOR(FunctionDescriptor.class);

        private final Class<?> type;

        FFMTypes(Class<?> type) {
            this.type = type;
        }

        @Override
        public Set<TypeAttr.ReferenceType> getUseImportTypes() {
            return Set.of(this);
        }

        @Override
        public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
            return Set.of();
        }

        @Override
        public Set<TypeAttr.GenerationType> toGenerationTypes() {
            return Set.of(this);
        }

        public Class<?> getType() {
            return type;
        }
    }


    /**
     * generated, essential types
     */
    public sealed interface BaseType extends TypeAttr.ReferenceType, TypeAttr.GenerationType permits BindTypes, FFMTypes, ListTypes, SpecificTypes, ValueInterface {

    }
}