package generator.types;

import generator.types.operations.ValueBased;
import generator.types.operations.OperationAttr;

import java.lang.foreign.ValueLayout;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CommonTypes {
    public enum Primitives implements BaseType {
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
        public Set<TypeAttr.Type> getReferenceTypes() {
            return Set.of(this);
        }

        @Override
        public Set<TypeAttr.Type> getDefineReferTypes() {
            return Set.of();
        }

        @Override
        public Set<TypeAttr.Type> toGenerationTypes() {
            return Set.of(this);
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

    public enum BindTypes implements BaseType, TypeAttr.ValueBased {
        I8("BasicI8", Primitives.JAVA_BYTE),
        I16("BasicI16", Primitives.JAVA_SHORT),
        I32("BasicI32", Primitives.JAVA_INT),
        I64("BasicI64", Primitives.JAVA_LONG),
        FP32("BasicFP32", Primitives.JAVA_FLOAT),
        FP64("BasicFP64", Primitives.JAVA_DOUBLE),
        Pointer("BasicPointer", Primitives.ADDRESS),
        FP16("BasicFP16", Primitives.FLOAT16),
        FP128("BasicFP128", Primitives.LONG_DOUBLE),
        I128("BasicI128", Primitives.Integer128);
        private final String rawName;
        private final Primitives primitives;

        BindTypes(String rawName, Primitives primitives) {
            this.rawName = rawName;
            this.primitives = primitives;
        }

        @Override
        public Set<TypeAttr.Type> getReferenceTypes() {
            return Set.of(this);
        }

        @Override
        public Set<TypeAttr.Type> getDefineReferTypes() {
            return primitives.getReferenceTypes();
        }

        @Override
        public Set<TypeAttr.Type> toGenerationTypes() {
            return Set.of(this);
        }

        public OperationAttr.Operation getOperation() {
            return new ValueBased(rawName, primitives);
        }

        public String getRawName() {
            return rawName;
        }

        public String getGenericName(String t) {
            return rawName + "<%s>".formatted(t);
        }

        public Primitives getPrimitiveType() {
            return primitives;
        }

        public ListTypes getListType() {
            return Arrays.stream(ListTypes.values()).filter(listTypes -> listTypes.elementType.equals(this)).findFirst().orElseThrow();
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
        public Set<TypeAttr.Type> getReferenceTypes() {
            return Set.of(this);
        }

        @Override
        public Set<TypeAttr.Type> getDefineReferTypes() {
            return elementType.getReferenceTypes();
        }

        @Override
        public Set<TypeAttr.Type> toGenerationTypes() {
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
        NList, NString, PtrList, SymbolProvider, AbstractNativeList;

        @Override
        public Set<TypeAttr.Type> getReferenceTypes() {
            return Set.of(this);
        }

        @Override
        public Set<TypeAttr.Type> getDefineReferTypes() {
            return Set.of();
        }

        @Override
        public Set<TypeAttr.Type> toGenerationTypes() {
            return Set.of(this);
        }
    }

    /**
     * generated, essential types
     */
    public sealed interface BaseType extends TypeAttr.Type permits BindTypes, ListTypes, Primitives, SpecificTypes {

    }
}