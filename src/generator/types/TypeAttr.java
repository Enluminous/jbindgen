package generator.types;

import generator.types.operations.OperationAttr;

import java.util.Set;

public class TypeAttr {
    private TypeAttr() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * types that have size, layout, operations
     */
    public sealed interface SizedType extends Type permits AbstractGenerationType, PointerType {
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

    public sealed interface OperationType extends Type permits AbstractGenerationType, PointerType {
        /**
         * ways to construct, destruct the type
         */
        OperationAttr.Operation getOperation();
    }

    /**
     * indicate the type is value based, can be invoked as function parameter
     * or return value without {@link java.lang.foreign.SegmentAllocator}
     */
    public interface ValueBased {
    }

    public interface NamedType {

        /**
         * get the type name in java
         *
         * @return the type name
         */
        String typeName();
    }

    /**
     * types have generation
     */
    public sealed interface GenerationType extends Type permits AbstractGenerationType, CommonTypes.BaseType, FunctionPtrType, RefOnlyType {

    }

    public sealed interface ReferenceType extends Type permits AbstractGenerationType, CommonTypes.BaseType, PointerType, RefOnlyType, VoidType {
        /**
         * @return the types when reference this type
         */
        Set<ReferenceType> getReferenceTypes();

        /**
         * @return the types used when define this type
         * @implNote do not include self
         */
        Set<ReferenceType> getDefineReferTypes();

        /**
         * @return the generation types of this type
         */
        Set<GenerationType> toGenerationTypes();
    }

    // root type
    public sealed interface Type permits ReferenceType, GenerationType, OperationType, SizedType {
    }
}
