package generator.types;

import generator.types.operations.OperationAttr;

import java.util.Objects;
import java.util.Set;

public class TypeAttr {
    /**
     * types that have size, layout, operations
     */
    public sealed interface NormalType extends NType
            permits AbstractType {

        /**
         * ways to construct, destruct the type
         */
        OperationAttr.Operation getOperation();

        /**
         * get the string of {@link java.lang.foreign.MemoryLayout}
         *
         * @return the string presentation of the MemoryLayout
         */
        String getMemoryLayout();

        /**
         * sizeof(Type)
         *
         * @return the byteSize of the type
         */
        long getByteSize();
    }

    public sealed abstract static class AbstractType
            implements NormalType
            permits ArrayType, EnumType, FunctionPtrType, PointerType, StructType, ValueBasedType {
        protected final long byteSize;
        protected final String memoryLayout;
        protected final String typeName;

        public AbstractType(long byteSize, String memoryLayout, String typeName) {
            this.byteSize = byteSize;
            this.memoryLayout = memoryLayout;
            this.typeName = typeName;
        }

        @Override
        public long getByteSize() {
            return byteSize;
        }

        @Override
        public String getMemoryLayout() {
            return memoryLayout;
        }

        @Override
        public String typeName() {
            return typeName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AbstractType that)) return false;
            return byteSize == that.byteSize && Objects.equals(memoryLayout, that.memoryLayout) && Objects.equals(typeName, that.typeName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(byteSize, memoryLayout, typeName);
        }

        @Override
        public String toString() {
            return "AbstractType{" +
                   "typeName='" + typeName + '\'' +
                   '}';
        }
    }

    /**
     * indicate the type is value based, can be invoked as function parameter
     * or return value without {@link java.lang.foreign.SegmentAllocator}
     */
    public sealed interface ValueBased permits CommonTypes.BindTypes, ValueBasedType {

    }

    /**
     * the types come from native part
     */
    public sealed interface NType extends Type permits RefOnlyType, NormalType, VoidType {
        /**
         * get the type name in java
         *
         * @return the type name
         */
        String typeName();
    }

    // root type
    public sealed interface Type permits CommonTypes.BaseType, NType {
        /**
         * @return the type referenced types
         * @implNote do not return it-self type
         */
        Set<Type> getReferencedTypes();
    }

    public static boolean isValueBased(NType type) {
        return type != null && type instanceof ValueBased;
    }
}
