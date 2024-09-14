package generator.types;

import java.util.List;
import java.util.Optional;

public final class Function implements TypeAttr.Type {
    private final String typeName;

    public record Arg(String argName, TypeAttr.NormalType type) {
    }

    private final List<Arg> args;

    private final Optional<TypeAttr.NormalType> returnType;

    public Function(String typeName, List<Arg> args, TypeAttr.Type retType) {
        this.typeName = typeName;
        this.args = args;
        returnType = switch (retType) {
            case TypeAttr.NormalType a -> Optional.of(a);
            case VoidType _, Function _ -> Optional.empty();
        };
    }

    @Override
    public String getTypeName() {
        return typeName;
    }
}
