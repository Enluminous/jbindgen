package generator.types;

public sealed interface IGenerationType extends
        TypeAttr.SizedType,
        TypeAttr.OperationType,
        TypeAttr.NamedType,
        TypeAttr.ReferenceType,
        TypeAttr.GenerationType permits
        AbstractGenerationType,
        ArrayType,
        CommonTypes.BindTypes,
        PointerType {
}
