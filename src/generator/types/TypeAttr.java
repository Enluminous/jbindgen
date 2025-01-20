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
    public sealed interface SizedType permits IGenerationType {
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

    public sealed interface OperationType permits IGenerationType {
        /**
         * ways to construct, destruct the type
         */
        OperationAttr.Operation getOperation();
    }

    public sealed interface NamedType permits IGenerationType, RefOnlyType, VoidType {

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
    public sealed interface GenerationType permits IGenerationType, CommonTypes.BaseType, RefOnlyType {

    }

    public sealed interface ReferenceType permits IGenerationType, CommonTypes.BaseType, RefOnlyType, VoidType {
        /**
         * @return the types when use this type
         */
        Set<ReferenceType> getUseImportTypes();

        /**
         * @return the types used when define this type
         * @implNote do not include self
         */
        Set<ReferenceType> getDefineImportTypes();

        /**
         * @return the generation types of this type
         */
        Set<GenerationType> toGenerationTypes();
    }
}
