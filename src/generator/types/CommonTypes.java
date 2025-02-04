package generator.types;

import generator.types.operations.DestructOnlyOp;
import generator.types.operations.OperationAttr;
import generator.types.operations.ValueBased;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CommonTypes {
    public enum Primitives {
        JAVA_BOOLEAN("ValueLayout.JAVA_BOOLEAN", "ValueLayout.OfBoolean", "boolean", "Boolean", null, AddressLayout.JAVA_BOOLEAN.byteSize(), false, "Byte"),
        JAVA_BYTE("ValueLayout.JAVA_BYTE", "ValueLayout.OfBye", "byte", "Byte", null, AddressLayout.JAVA_BYTE.byteSize(), false, "Byte"),
        JAVA_SHORT("ValueLayout.JAVA_SHORT", "ValueLayout.OfShort", "short", "Short", null, AddressLayout.JAVA_SHORT.byteSize(), false, "Short"),
        JAVA_CHAR("ValueLayout.JAVA_CHAR", "ValueLayout.OfChar", "char", "Character", null, AddressLayout.JAVA_CHAR.byteSize(), false, "Char"),
        JAVA_INT("ValueLayout.JAVA_INT", "ValueLayout.OfInt", "int", "Integer", null, AddressLayout.JAVA_INT.byteSize(), false, "Int"),
        JAVA_LONG("ValueLayout.JAVA_LONG", "ValueLayout.OfLong", "long", "Long", null, AddressLayout.JAVA_LONG.byteSize(), false, "Long"),
        JAVA_FLOAT("ValueLayout.JAVA_FLOAT", "ValueLayout.OfFloat", "float", "Float", null, AddressLayout.JAVA_FLOAT.byteSize(), false, "Float"),
        JAVA_DOUBLE("ValueLayout.JAVA_DOUBLE", "ValueLayout.OfDouble", "double", "Double", null, AddressLayout.JAVA_DOUBLE.byteSize(), false, "Double"),
        ADDRESS("ValueLayout.ADDRESS", "AddressLayout", "MemorySegment", "MemorySegment", FFMTypes.MEMORY_SEGMENT, AddressLayout.ADDRESS.byteSize(), false, "Addr"),
        FLOAT16("ValueLayout.JAVA_SHORT", "AddressLayout.OfFloat", null, null, null, AddressLayout.JAVA_SHORT.byteSize(), true, null),
        LONG_DOUBLE("MemoryLayout.structLayout(ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG)", "MemoryLayout", null, null, null, AddressLayout.JAVA_LONG.byteSize() * 2, true, null),
        Integer128("MemoryLayout.structLayout(ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG)", "MemoryLayout", null, null, null, AddressLayout.JAVA_LONG.byteSize() * 2, true, null);

        private final String memoryLayout;
        private final String typeName;
        private final String primitiveTypeName;
        private final String boxedTypeName;
        private final FFMTypes ffmType;
        private final long byteSize;
        private final boolean disabled;
        private final String memoryUtilName;

        Primitives(String memoryLayout, String typeName, String primitiveTypeName, String boxedTypeName, FFMTypes ffmType, long byteSize, boolean disabled, String memoryUtilName) {
            this.memoryLayout = memoryLayout;
            this.typeName = typeName;
            this.primitiveTypeName = primitiveTypeName;
            this.boxedTypeName = boxedTypeName;
            this.ffmType = ffmType;
            this.byteSize = byteSize;
            this.disabled = disabled;
            this.memoryUtilName = memoryUtilName;
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

        public String getMemoryUtilName() {
            return memoryUtilName;
        }
    }

    public enum BasicOperations implements BaseType, TypeAttr.OperationType {
        Operation(false),
        Info(Set.of(Operation, FFMTypes.MEMORY_SEGMENT), false),
        Value(Set.of(Operation), false),
        PteI(Set.of(Value, Operation, FFMTypes.MEMORY_SEGMENT), false),//pointee
        ArrayI(Set.of(Value, FFMTypes.MEMORY_SEGMENT), true),
        StructI(Set.of(Value, FFMTypes.MEMORY_SEGMENT), true),
        ;
        private final Set<TypeAttr.TypeRefer> imports;
        private final boolean destruct;

        BasicOperations(Set<TypeAttr.TypeRefer> imports, boolean destruct) {
            this.imports = imports;
            this.destruct = destruct;
        }

        BasicOperations(boolean destruct) {
            this.destruct = destruct;
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
            return name();
        }

        @Override
        public OperationAttr.Operation getOperation() {
            if (!destruct) {
                throw new UnsupportedOperationException();
            }
            return new DestructOnlyOp(Primitives.ADDRESS);
        }
    }

    public enum ValueInterface implements BaseType, TypeAttr.OperationType {
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
            return name();
        }

        public Primitives getPrimitive() {
            return primitive;
        }

        @Override
        public OperationAttr.Operation getOperation() {
            return new DestructOnlyOp(primitive);
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
        PtrOp(ValueInterface.PtrI, Set.of(BasicOperations.PteI)),
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
            holders.addAll(BasicOperations.Info.getUseImportTypes());
            holders.addAll(SpecificTypes.MemoryUtils.getUseImportTypes());
            holders.addAll(BasicOperations.Value.getUseImportTypes());
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
            return name();
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
        I8(BindTypeOperations.I8Op),
        I16(BindTypeOperations.I16Op),
        I32(BindTypeOperations.I32Op),
        I64(BindTypeOperations.I64Op),
        FP32(BindTypeOperations.FP32Op),
        FP64(BindTypeOperations.FP64Op),
        Ptr(BindTypeOperations.PtrOp, Set.of(FFMTypes.MEMORY_SEGMENT, SpecificTypes.ArrayOp, BasicOperations.Value, ValueInterface.PtrI), true),
        FP16(BindTypeOperations.FP16Op),
        FP128(BindTypeOperations.FP128Op),
        I128(BindTypeOperations.I128Op);
        private final BindTypeOperations operations;
        private final Set<TypeAttr.TypeRefer> referenceTypes;
        private final boolean generic;

        BindTypes(BindTypeOperations operations, Set<TypeAttr.TypeRefer> referenceTypes, boolean generic) {
            this.operations = operations;
            this.referenceTypes = referenceTypes;
            this.generic = generic;
        }

        BindTypes(BindTypeOperations operations) {
            this.operations = operations;
            this.referenceTypes = Set.of();
            generic = false;
        }

        public static String makePtrGenericName(String t) {
            return Ptr.name() + "<%s>".formatted(t);
        }

        public static String makePtrWildcardName(String t) {
            return Ptr.name() + "<? extends %s>".formatted(t);
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
            holders.addAll(SpecificTypes.Array.getUseImportTypes());
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
            return new ValueBased(this, name(), this);
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
            return name();
        }

        public Primitives getPrimitiveType() {
            return operations.value.primitive;
        }
    }

    public enum SpecificTypes implements BaseType {
        Utils(false, Set::of),
        ArrayOp(true, () -> Set.of(BindTypeOperations.PtrOp, BasicOperations.Value, BasicOperations.Info,
                FFMTypes.MEMORY_SEGMENT, BasicOperations.ArrayI, BindTypes.Ptr)),
        Array(true, () -> Set.of(FFMTypes.MEMORY_SEGMENT, FFMTypes.VALUE_LAYOUT, FFMTypes.SEGMENT_ALLOCATOR, ArrayOp,
                BasicOperations.Operation, BasicOperations.Info, ValueInterface.PtrI, BindTypes.Ptr, BindTypeOperations.PtrOp)),
        StructOp(true, () -> Set.of(BindTypes.Ptr, BasicOperations.Value, BasicOperations.Info,
                FFMTypes.MEMORY_SEGMENT, BasicOperations.StructI)),
        MemoryUtils(false, () -> Set.of(FFMTypes.MEMORY_SEGMENT, FFMTypes.VALUE_LAYOUT)),
        Str(false, () -> Set.of(ArrayOp, BasicOperations.Info, Array, BindTypes.I8, BindTypes.Ptr));

        final boolean generic;
        // late init
        private final Supplier<Set<TypeAttr.TypeRefer>> referenceTypes;

        SpecificTypes(boolean generic, Supplier<Set<TypeAttr.TypeRefer>> referenceTypes) {
            this.generic = generic;
            this.referenceTypes = referenceTypes;
        }

        @Override
        public Set<Holder<TypeAttr.TypeRefer>> getUseImportTypes() {
            return Set.of(new Holder<>(this));
        }

        @Override
        public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
            return referenceTypes.get().stream().map(TypeAttr.TypeRefer::getUseImportTypes).flatMap(Set::stream).collect(Collectors.toSet());
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
            return name();
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