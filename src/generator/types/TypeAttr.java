package generator.types;

import generator.operatons.OperationAttr;

public class TypeAttr {
    /**
     * types that have size, layout, operations
     */
    public sealed interface NormalType extends Type
            permits Primitives, AbstractType {
        // get operate
        OperationAttr.Operation getOperation();

        /**
         * get the string of {@link java.lang.foreign.MemoryLayout}
         * @return the string presentation of the MemoryLayout
         */
        String getMemoryLayout();

        /**
         * sizeof(Type)
         * @return the byteSize of the type
         */
        long getByteSize();
    }

    public sealed abstract static class AbstractType
            implements NormalType
            permits Enum, Pointer, Struct, ValueBasedType {
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

        public String getTypeName() {
            return typeName;
        }
    }

    /**
     * indicate the type is value based, can be invoked as function parameter
     * or return value without {@link java.lang.foreign.SegmentAllocator}
     */
    public interface ValueBased {

    }

    // root type
    public sealed interface Type permits Function, NormalType, VoidType {
        /**
         * get the type name in java
         * @return the type name
         */
        String getTypeName();
    }
}
