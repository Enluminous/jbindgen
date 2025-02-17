package generator.types.operations;

import generator.types.CommonTypes;
import generator.types.TypeAttr;

import java.util.HashSet;
import java.util.Set;

public interface CommonOperation {
    interface UpperType {
        String typeName(TypeAttr.NameType nameType);

        Set<TypeAttr.TypeRefer> typeRefers();

        TypeAttr.OperationType typeOp();

        default boolean rejectWildcard() {
            return false;
        }
    }

    UpperType getUpperType();

    record End(TypeAttr.NamedType type, String typeName, boolean rejectType) implements UpperType {
        public End(TypeAttr.NamedType type) {
            this(type, type.typeName(TypeAttr.NameType.RAW), false);
        }

        public End(TypeAttr.NamedType type, String typeName) {
            this(type, typeName, false);
        }

        @Override
        public String typeName(TypeAttr.NameType nameType) {
            return typeName;
        }

        @Override
        public Set<TypeAttr.TypeRefer> typeRefers() {
            return rejectType ? Set.of() : Set.of(((TypeAttr.TypeRefer) type));
        }

        @Override
        public TypeAttr.OperationType typeOp() {
            return ((TypeAttr.OperationType) type);
        }

        @Override
        public boolean rejectWildcard() {
            return rejectType;
        }
    }

    record Warp(TypeAttr.NamedType outer, UpperType inner) implements UpperType {
        public Warp(TypeAttr.NamedType outer, CommonOperation inner) {
            this(outer, inner.getUpperType());
        }

        @Override
        public String typeName(TypeAttr.NameType nameType) {
            return switch (nameType) {
                case WILDCARD -> inner.rejectWildcard()
                        ? outer.typeName(TypeAttr.NameType.RAW) + "<?>"
                        : outer.typeName(TypeAttr.NameType.RAW) + "<? extends %s>".formatted(inner.typeName(nameType));
                case GENERIC -> outer.typeName(TypeAttr.NameType.RAW) + "<%s>".formatted(inner.typeName(nameType));
                case RAW -> outer.typeName(TypeAttr.NameType.RAW);
            };
        }

        @Override
        public Set<TypeAttr.TypeRefer> typeRefers() {
            var typeRefers = new HashSet<>(inner.typeRefers());
            typeRefers.add(((TypeAttr.TypeRefer) outer));
            return typeRefers;
        }

        @Override
        public TypeAttr.OperationType typeOp() {
            return ((TypeAttr.OperationType) outer);
        }
    }

    record Operation(String str, Set<TypeAttr.TypeRefer> typeRefers) {
    }

    Operation makeOperation();

    static Operation makeStaticOperation(String typeName) {
        return new Operation(typeName + ".OPERATIONS", Set.of());
    }

    static Operation makeVoidOperation() {
        return new Operation(CommonTypes.BasicOperations.Info.typeName(TypeAttr.NameType.RAW) + ".makeOperations()",
                Set.of(CommonTypes.BasicOperations.Info));
    }
}
