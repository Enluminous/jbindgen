package generator.types;

import generator.types.operations.OperationAttr;
import generator.types.operations.ValueBased;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CommonTypes {
    public enum Primitives {
        JAVA_BOOLEAN("AddressLayout.JAVA_BOOLEAN", "AddressLayout.OfBoolean", "boolean", "Boolean", null, AddressLayout.JAVA_BOOLEAN.byteSize(), false),
        JAVA_BYTE("AddressLayout.JAVA_BYTE", "AddressLayout.OfBye", "byte", "Byte", null, AddressLayout.JAVA_BYTE.byteSize(), false),
        JAVA_SHORT("AddressLayout.JAVA_SHORT", "AddressLayout.OfShort", "short", "Short", null, AddressLayout.JAVA_SHORT.byteSize(), false),
        JAVA_CHAR("AddressLayout.JAVA_CHAR", "AddressLayout.OfChar", "char", "Character", null, AddressLayout.JAVA_CHAR.byteSize(), false),
        JAVA_INT("AddressLayout.JAVA_INT", "AddressLayout.OfInt", "int", "Integer", null, AddressLayout.JAVA_INT.byteSize(), false),
        JAVA_LONG("AddressLayout.JAVA_LONG", "AddressLayout.OfLong", "long", "Long", null, AddressLayout.JAVA_LONG.byteSize(), false),
        JAVA_FLOAT("AddressLayout.JAVA_FLOAT", "AddressLayout.OfFloat", "float", "Float", null, AddressLayout.JAVA_FLOAT.byteSize(), false),
        JAVA_DOUBLE("AddressLayout.JAVA_DOUBLE", "AddressLayout.OfDouble", "double", "Double", null, AddressLayout.JAVA_DOUBLE.byteSize(), false),
        ADDRESS("AddressLayout.ADDRESS", "AddressLayout", "MemorySegment", "MemorySegment", FFMTypes.MEMORY_SEGMENT, AddressLayout.ADDRESS.byteSize(), false),
        FLOAT16("AddressLayout.JAVA_SHORT", "AddressLayout.OfFloat", null, null, null, AddressLayout.JAVA_SHORT.byteSize(), true),
        LONG_DOUBLE("MemoryLayout.structLayout(AddressLayout.JAVA_LONG, AddressLayout.JAVA_LONG)", "MemoryLayout", null, null, null, AddressLayout.JAVA_LONG.byteSize() * 2, true),
        Integer128("MemoryLayout.structLayout(AddressLayout.JAVA_LONG, AddressLayout.JAVA_LONG)", "MemoryLayout", null, null, null, AddressLayout.JAVA_LONG.byteSize() * 2, true);

        private final String memoryLayout;
        private final String typeName;
        private final String primitiveTypeName;
        private final String boxedTypeName;
        private final FFMTypes ffmType;
        private final long byteSize;
        private final boolean disabled;

        Primitives(String memoryLayout, String typeName, String primitiveTypeName, String boxedTypeName, FFMTypes ffmType, long byteSize, boolean disabled) {
            this.memoryLayout = memoryLayout;
            this.typeName = typeName;
            this.primitiveTypeName = primitiveTypeName;
            this.boxedTypeName = boxedTypeName;
            this.ffmType = ffmType;
            this.byteSize = byteSize;
            this.disabled = disabled;
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

        public boolean isDisabled() {
            return disabled;
        }
    }

    public enum BasicOperations implements BaseType {
        Operation(false),
        Info(Set.of(Operation, FFMTypes.MEMORY_SEGMENT), true),
        Value(Set.of(Operation), true),
        Pte(Set.of(Value, Operation), true);//pointee

        private final Set<TypeAttr.ReferenceType> imports;
        private final boolean generic;

        BasicOperations(Set<TypeAttr.ReferenceType> imports, boolean generic) {
            this.imports = imports;
            this.generic = generic;
        }

        BasicOperations(boolean generic) {
            this.generic = generic;
            this.imports = Set.of();
        }

        @Override
        public Set<TypeAttr.ReferenceType> getUseImportTypes() {
            return Set.of(this);
        }

        @Override
        public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
            return imports;
        }

        @Override
        public Optional<GenerationTypeHolder<BasicOperations>> toGenerationTypes() {
            return Optional.of(new GenerationTypeHolder<>(this));
        }

        @Override
        public String typeName(NameType nameType) {
            return switch (nameType) {
                case WILDCARD, GENERIC -> generic ? name() + "<?>" : name();
                case RAW -> name();
            };
        }
    }

    public enum ValueInterface implements BaseType {
        I8I(Primitives.JAVA_BYTE),
        I16I(Primitives.JAVA_SHORT),
        I32I(Primitives.JAVA_INT),
        I64I(Primitives.JAVA_LONG),
        FP32I(Primitives.JAVA_FLOAT),
        FP64I(Primitives.JAVA_DOUBLE),
        PtrI(Set.of(BasicOperations.Value, FFMTypes.MEMORY_SEGMENT), Primitives.ADDRESS),
        FP16I(Primitives.FLOAT16),
        FP128I(Primitives.LONG_DOUBLE),
        I128I(Primitives.Integer128);

        private final Set<TypeAttr.ReferenceType> imports;
        private final Primitives primitive;

        ValueInterface(Set<TypeAttr.ReferenceType> imports, Primitives primitive) {
            this.imports = imports;
            this.primitive = primitive;
        }

        ValueInterface(Primitives primitive) {
            this.primitive = primitive;
            this.imports = Set.of(BasicOperations.Value);
        }

        @Override
        public Set<TypeAttr.ReferenceType> getUseImportTypes() {
            return Set.of(this);
        }

        @Override
        public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
            return imports;
        }

        @Override
        public Optional<GenerationTypeHolder<ValueInterface>> toGenerationTypes() {
            return Optional.of(new GenerationTypeHolder<>(this));
        }

        @Override
        public String typeName(NameType nameType) {
            return switch (nameType) {
                case GENERIC, WILDCARD -> name() + "<?>";
                case RAW -> name();
            };
        }

        public Primitives getPrimitive() {
            return primitive;
        }
    }

    /**
     * interface of {@link BindTypes}
     */
    public enum BindTypeOperations implements BaseType {
        I8Op(ValueInterface.I8I),
        I16Op(ValueInterface.I16I),
        I32Op(ValueInterface.I32I),
        I64Op(ValueInterface.I64I),
        FP32Op(ValueInterface.FP32I),
        FP64Op(ValueInterface.FP64I),
        PtrOp(ValueInterface.PtrI),
        FP16Op(ValueInterface.FP16I),
        FP128Op(ValueInterface.FP128I),
        I128Op(ValueInterface.I128I);
        private final ValueInterface value;

        BindTypeOperations(ValueInterface elementType) {
            this.value = elementType;
        }

        @Override
        public Set<TypeAttr.ReferenceType> getUseImportTypes() {
            return Set.of(this);
        }

        @Override
        public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
            return value.getUseImportTypes();
        }

        @Override
        public Optional<GenerationTypeHolder<BindTypeOperations>> toGenerationTypes() {
            return Optional.of(new GenerationTypeHolder<>(this));
        }

        public ValueInterface getValue() {
            return value;
        }

        @Override
        public String typeName(NameType nameType) {
            return switch (nameType) {
                case WILDCARD, GENERIC -> name() + "<?>";
                case RAW -> name();
            };
        }
    }

    public enum BindTypes implements
            BaseType,
            TypeAttr.SizedType,
            TypeAttr.OperationType,
            TypeAttr.NamedType,
            TypeAttr.ReferenceType,
            TypeAttr.GenerationType {
        I8("BasicI8", BindTypeOperations.I8Op),
        I16("BasicI16", BindTypeOperations.I16Op),
        I32("BasicI32", BindTypeOperations.I32Op),
        I64("BasicI64", BindTypeOperations.I64Op),
        FP32("BasicFP32", BindTypeOperations.FP32Op),
        FP64("BasicFP64", BindTypeOperations.FP64Op),
        Pointer("BasicPointer", BindTypeOperations.PtrOp),
        FP16("BasicFP16", BindTypeOperations.FP16Op),
        FP128("BasicFP128", BindTypeOperations.FP128Op),
        I128("BasicI128", BindTypeOperations.I128Op);
        private final String rawName;
        private final BindTypeOperations operations;

        BindTypes(String rawName, BindTypeOperations operations) {
            this.rawName = rawName;
            this.operations = operations;
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
            return operations.getUseImportTypes();
        }

        public BindTypeOperations getOperations() {
            return operations;
        }

        @Override
        public Optional<GenerationTypeHolder<BindTypes>> toGenerationTypes() {
            return Optional.of(new GenerationTypeHolder<>(this));
        }

        public OperationAttr.Operation getOperation() {
            return new ValueBased(rawName, operations.value.primitive);
        }

        public String getRawName() {
            return rawName;
        }

        @Override
        public String getMemoryLayout() {
            return operations.value.primitive.getMemoryLayout();
        }

        @Override
        public long getByteSize() {
            return operations.value.primitive.getByteSize();
        }

        @Override
        public String typeName(NameType nameType) {
            return switch (nameType) {
                case WILDCARD, GENERIC -> rawName + "<?>";
                case RAW -> rawName;
            };
        }

        public Primitives getPrimitiveType() {
            return operations.value.primitive;
        }
    }

    public enum SpecificTypes implements BaseType {
        AbstractNativeList(true, Set.of()),
        Utils(false, Set.of()),
        Array(true, Set.of(AbstractNativeList)),
        NString(false, Set.of(Array));

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
        public Optional<GenerationTypeHolder<SpecificTypes>> toGenerationTypes() {
            return Optional.of(new GenerationTypeHolder<>(this));
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

        public String getRawName() {
            return name();
        }

        @Override
        public String typeName(NameType nameType) {
            return switch (nameType) {
                case WILDCARD, GENERIC -> generic ? name() + "<?>" : name();
                case RAW -> name();
            };
        }
    }


    public enum FFMTypes implements BaseType {
        MEMORY_SEGMENT(MemorySegment.class),
        MEMORY_LAYOUT(MemoryLayout.class),
        ADDRESS_LAYOUT(AddressLayout.class),
        ARENA(Arena.class),
        METHOD_HANDLES(MethodHandles.class),
        FUNCTION_DESCRIPTOR(FunctionDescriptor.class),
        SEGMENT_ALLOCATOR(SegmentAllocator.class),
        METHOD_HANDLE(MethodHandle.class);

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
        public Optional<GenerationTypeHolder<FFMTypes>> toGenerationTypes() {
            return Optional.of(new GenerationTypeHolder<>(this));
        }

        public Class<?> getType() {
            return type;
        }

        @Override
        public String typeName(NameType nameType) {
            return type.getSimpleName();
        }
    }


    /**
     * generated, essential types
     */
    public sealed interface BaseType extends TypeAttr.ReferenceType, TypeAttr.GenerationType, TypeAttr.NamedType permits BindTypes, FFMTypes, BindTypeOperations, BasicOperations, SpecificTypes, ValueInterface {

    }
}