package generator.types;

import generator.types.operations.OperationAttr;

import java.util.Optional;
import java.util.Set;

public class TypeAttr {
    private TypeAttr() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * types that have size, layout, operations
     */
    public sealed interface SizedType permits AbstractGenerationType, ArrayType, CommonTypes.BindTypes, PointerType {
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

    public sealed interface OperationType permits AbstractGenerationType, ArrayType, CommonTypes.BindTypes, PointerType {
        /**
         * ways to construct, destruct the type
         */
        OperationAttr.Operation getOperation();
    }

    public sealed interface NamedType permits AbstractGenerationType, ArrayType, CommonTypes.BindTypes, PointerType, RefOnlyType, SymbolProviderType, VoidType {
        enum NameType {
            WILDCARD,
            GENERIC,
            RAW
        }

        /**
         * get the type name in java
         *
         * @return the type name
         */
        String typeName(NameType nameType);
    }

    /**
     * types have generation
     */
    public sealed interface GenerationType permits AbstractGenerationType, ArrayType, CommonTypes.BaseType, CommonTypes.BindTypes, PointerType, RefOnlyType, SymbolProviderType, VoidType {
    }

    public sealed interface ReferenceType permits AbstractGenerationType, ArrayType, CommonTypes.BaseType, CommonTypes.BindTypes, PointerType, RefOnlyType, SymbolProviderType, VoidType {
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
        Optional<? extends GenerationTypeHolder<? extends GenerationType>> toGenerationTypes();
    }
}
