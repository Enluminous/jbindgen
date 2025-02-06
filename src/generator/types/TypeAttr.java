package generator.types;

import generator.types.operations.OperationAttr;

public class TypeAttr {
    private TypeAttr() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * types that have size, layout, operations
     */
    public sealed interface SizedType permits AbstractGenerationType, ArrayType, ArrayTypeNamed, CommonTypes.BindTypes, PointerType {
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

    public sealed interface OperationType permits AbstractGenerationType, ArrayType, ArrayTypeNamed, CommonTypes.BasicOperations, CommonTypes.BindTypes, CommonTypes.ValueInterface, PointerType, RefOnlyType, VoidType {
        /**
         * ways to construct, destruct the type
         */
        OperationAttr.Operation getOperation();
    }

    public enum NameType {
        WILDCARD,
        GENERIC,
        RAW
    }

    public sealed interface NamedType permits AbstractGenerationType, ArrayType, ArrayTypeNamed, CommonTypes.BaseType, CommonTypes.BindTypes, PointerType, RefOnlyType, SymbolProviderType, VoidType {

        /**
         * get the type name in java
         *
         * @return the type name
         */
        String typeName(TypeAttr.NameType nameType);
    }

    /**
     * types have generation
     */
    public sealed interface GenerationType permits AbstractGenerationType, ArrayTypeNamed, CommonTypes.BaseType, CommonTypes.BindTypes, RefOnlyType, SymbolProviderType, VoidType {
    }

    public sealed interface TypeRefer permits AbstractGenerationType, ArrayType, ArrayTypeNamed, CommonTypes.BaseType, CommonTypes.BindTypes, PointerType, RefOnlyType, SymbolProviderType, VoidType {
        /**
         * @return the types when use this type
         */
        TypeImports getUseImportTypes();

        /**
         * @return the types used when define this type
         * @implNote do not include self
         */
        TypeImports getDefineImportTypes();
    }
}
