package generator.types;

import generator.types.operations.OperationAttr;
import generator.types.operations.ValueBased;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CommonTypes {
    public enum Primitives {
        JAVA_BOOLEAN("ValueLayout.JAVA_BOOLEAN", "ValueLayout.OfBoolean", "boolean", "Boolean", null, AddressLayout.JAVA_BOOLEAN.byteSize(), false),
        JAVA_BYTE("ValueLayout.JAVA_BYTE", "ValueLayout.OfBye", "byte", "Byte", null, AddressLayout.JAVA_BYTE.byteSize(), false),
        JAVA_SHORT("ValueLayout.JAVA_SHORT", "ValueLayout.OfShort", "short", "Short", null, AddressLayout.JAVA_SHORT.byteSize(), false),
        JAVA_CHAR("ValueLayout.JAVA_CHAR", "ValueLayout.OfChar", "char", "Character", null, AddressLayout.JAVA_CHAR.byteSize(), false),
        JAVA_INT("ValueLayout.JAVA_INT", "ValueLayout.OfInt", "int", "Integer", null, AddressLayout.JAVA_INT.byteSize(), false),
        JAVA_LONG("ValueLayout.JAVA_LONG", "ValueLayout.OfLong", "long", "Long", null, AddressLayout.JAVA_LONG.byteSize(), false),
        JAVA_FLOAT("ValueLayout.JAVA_FLOAT", "ValueLayout.OfFloat", "float", "Float", null, AddressLayout.JAVA_FLOAT.byteSize(), false),
        JAVA_DOUBLE("ValueLayout.JAVA_DOUBLE", "ValueLayout.OfDouble", "double", "Double", null, AddressLayout.JAVA_DOUBLE.byteSize(), false),
        ADDRESS("ValueLayout.ADDRESS", "AddressLayout", "MemorySegment", "MemorySegment", FFMTypes.MEMORY_SEGMENT, AddressLayout.ADDRESS.byteSize(), false),
        FLOAT16("ValueLayout.JAVA_SHORT", "AddressLayout.OfFloat", null, null, null, AddressLayout.JAVA_SHORT.byteSize(), true),
        LONG_DOUBLE("MemoryLayout.structLayout(ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG)", "MemoryLayout", null, null, null, AddressLayout.JAVA_LONG.byteSize() * 2, true),
        Integer128("MemoryLayout.structLayout(ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG)", "MemoryLayout", null, null, null, AddressLayout.JAVA_LONG.byteSize() * 2, true);

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

        public boolean enable() {
            return !disabled;
        }
    }

    public enum BasicOperations implements BaseType {
        Operation(false),
        Info(Set.of(Operation, FFMTypes.MEMORY_SEGMENT), true),
        Value(Set.of(Operation), true),
        Pte(Set.of(Value, Operation, FFMTypes.MEMORY_SEGMENT), true),//pointee
        ;
        private final Set<TypeAttr.TypeRefer> imports;
        private final boolean generic;

        BasicOperations(Set<TypeAttr.TypeRefer> imports, boolean generic) {
            this.imports = imports;
            this.generic = generic;
        }

        BasicOperations(boolean generic) {
            this.generic = generic;
            this.imports = Set.of();
        }

        @Override
        public Set<Holder<TypeAttr.TypeRefer>> getUseImportTypes() {
            return Set.of(new Holder<>(this));
        }

        @Override
        public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
            return imports.stream().map(TypeAttr.TypeRefer::getUseImportTypes).flatMap(Set::stream).collect(Collectors.toSet());
        }

        @Override
        public Optional<Holder<BasicOperations>> toGenerationTypes() {
            return Optional.of(new Holder<>(this));
        }

        @Override
        public String typeName(TypeAttr.NameType nameType) {
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

        private final Set<TypeAttr.TypeRefer> imports;
        private final Primitives primitive;

        ValueInterface(Set<TypeAttr.TypeRefer> imports, Primitives primitive) {
            this.imports = imports;
            this.primitive = primitive;
        }

        ValueInterface(Primitives primitive) {
            this.primitive = primitive;
            this.imports = Set.of(BasicOperations.Value);
        }

        @Override
        public Set<Holder<TypeAttr.TypeRefer>> getUseImportTypes() {
            return Set.of(new Holder<>(this));
        }

        @Override
        public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
            return imports.stream().map(TypeAttr.TypeRefer::getUseImportTypes).flatMap(Set::stream).collect(Collectors.toSet());
        }

        @Override
        public Optional<Holder<ValueInterface>> toGenerationTypes() {
            return Optional.of(new Holder<>(this));
        }

        @Override
        public String typeName(TypeAttr.NameType nameType) {
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
        PtrOp(ValueInterface.PtrI, Set.of(BasicOperations.Pte)),
        FP16Op(ValueInterface.FP16I),
        FP128Op(ValueInterface.FP128I),
        I128Op(ValueInterface.I128I);
        private final ValueInterface value;
        private final Set<TypeAttr.TypeRefer> referenceTypes;

        BindTypeOperations(ValueInterface elementType, Set<TypeAttr.TypeRefer> referenceTypes) {
            this.value = elementType;
            this.referenceTypes = referenceTypes;
        }

        BindTypeOperations(ValueInterface elementType) {
            this.value = elementType;
            this.referenceTypes = Set.of();
        }

        @Override
        public Set<Holder<TypeAttr.TypeRefer>> getUseImportTypes() {
            return Set.of(new Holder<>(this));
        }

        @Override
        public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
            var holders = new HashSet<>(value.getUseImportTypes());
            holders.addAll(FFMTypes.VALUE_LAYOUT.getUseImportTypes());
            for (TypeAttr.TypeRefer referenceType : referenceTypes) {
                holders.addAll(referenceType.getUseImportTypes());
            }
            return holders;
        }

        @Override
        public Optional<Holder<BindTypeOperations>> toGenerationTypes() {
            return Optional.of(new Holder<>(this));
        }

        public ValueInterface getValue() {
            return value;
        }

        @Override
        public String typeName(TypeAttr.NameType nameType) {
            return switch (nameType) {
                case WILDCARD, GENERIC -> name() + "<?>";
                case RAW -> name();
            };
        }

        public String operatorTypeName() {
            return name() + "I";
        }
    }

    public enum BindTypes implements
            BaseType,
            TypeAttr.SizedType,
            TypeAttr.OperationType,
            TypeAttr.NamedType,
            TypeAttr.TypeRefer,
            TypeAttr.GenerationType {
        I8("I8", BindTypeOperations.I8Op),
        I16("I16", BindTypeOperations.I16Op),
        I32("I32", BindTypeOperations.I32Op),
        I64("I64", BindTypeOperations.I64Op),
        FP32("FP32", BindTypeOperations.FP32Op),
        FP64("FP64", BindTypeOperations.FP64Op),
        Ptr("Ptr", BindTypeOperations.PtrOp, Set.of(FFMTypes.MEMORY_SEGMENT)),
        FP16("FP16", BindTypeOperations.FP16Op),
        FP128("FP128", BindTypeOperations.FP128Op),
        I128("I128", BindTypeOperations.I128Op);
        private final String rawName;
        private final BindTypeOperations operations;
        private final Set<TypeAttr.TypeRefer> referenceTypes;

        BindTypes(String rawName, BindTypeOperations operations, Set<TypeAttr.TypeRefer> referenceTypes) {
            this.rawName = rawName;
            this.operations = operations;
            this.referenceTypes = referenceTypes;
        }

        BindTypes(String rawName, BindTypeOperations operations) {
            this.rawName = rawName;
            this.operations = operations;
            this.referenceTypes = Set.of();
        }

        public static String makePtrGenericName(String t) {
            return Ptr.rawName + "<%s>".formatted(t);
        }

        public static String makePtrWildcardName(String t) {
            return Ptr.rawName + "<? extends %s>".formatted(t);
        }

        @Override
        public Set<Holder<TypeAttr.TypeRefer>> getUseImportTypes() {
            return Set.of(new Holder<>(this));
        }

        @Override
        public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
            var holders = new HashSet<>(operations.getUseImportTypes());
            holders.addAll(FFMTypes.VALUE_LAYOUT.getUseImportTypes());
            holders.addAll(FFMTypes.SEGMENT_ALLOCATOR.getUseImportTypes());
            holders.addAll(BasicOperations.Info.getUseImportTypes());
            for (TypeAttr.TypeRefer referenceType : referenceTypes) {
                holders.addAll(referenceType.getUseImportTypes());
            }
            return holders;
        }

        public BindTypeOperations getOperations() {
            return operations;
        }

        @Override
        public Optional<Holder<BindTypes>> toGenerationTypes() {
            return Optional.of(new Holder<>(this));
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
        public String typeName(TypeAttr.NameType nameType) {
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
        ArrayOp(true, Set.of(BindTypeOperations.PtrOp, BasicOperations.Value, BasicOperations.Info, FFMTypes.MEMORY_SEGMENT)),
        Array(true, Set.of(FFMTypes.MEMORY_SEGMENT, FFMTypes.VALUE_LAYOUT, FFMTypes.SEGMENT_ALLOCATOR)),
        StructOp(true, Set.of(BindTypeOperations.PtrOp, BasicOperations.Value, BasicOperations.Info, FFMTypes.MEMORY_SEGMENT)),
        NStr(false, Set.of(ArrayOp, BasicOperations.Info, Array, BindTypes.I8));

        final boolean generic;
        private final Set<TypeAttr.TypeRefer> referenceTypes;

        SpecificTypes(boolean generic, Set<TypeAttr.TypeRefer> referenceTypes) {
            this.generic = generic;
            this.referenceTypes = referenceTypes;
        }

        @Override
        public Set<Holder<TypeAttr.TypeRefer>> getUseImportTypes() {
            return Set.of(new Holder<>(this));
        }

        @Override
        public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
            return referenceTypes.stream().map(TypeAttr.TypeRefer::getUseImportTypes).flatMap(Set::stream).collect(Collectors.toSet());
        }

        @Override
        public Optional<Holder<SpecificTypes>> toGenerationTypes() {
            return Optional.of(new Holder<>(this));
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
        public String typeName(TypeAttr.NameType nameType) {
            return switch (nameType) {
                case WILDCARD, GENERIC -> generic ? name() + "<?>" : name();
                case RAW -> name();
            };
        }
    }


    public enum FFMTypes implements BaseType {
        MEMORY_SEGMENT(MemorySegment.class),
        MEMORY_LAYOUT(MemoryLayout.class),
        VALUE_LAYOUT(ValueLayout.class),
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
        public Set<Holder<TypeAttr.TypeRefer>> getUseImportTypes() {
            return Set.of(new Holder<>(this));
        }

        @Override
        public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
            return Set.of();
        }

        @Override
        public Optional<Holder<FFMTypes>> toGenerationTypes() {
            return Optional.of(new Holder<>(this));
        }

        public Class<?> getType() {
            return type;
        }

        @Override
        public String typeName(TypeAttr.NameType nameType) {
            return type.getSimpleName();
        }
    }


    /**
     * generated, essential types
     */
    public sealed interface BaseType extends TypeAttr.TypeRefer, TypeAttr.GenerationType, TypeAttr.NamedType permits BindTypes, FFMTypes, BindTypeOperations, BasicOperations, SpecificTypes, ValueInterface {
        @Override
        Optional<? extends Holder<? extends BaseType>> toGenerationTypes();
    }
}