package generator;

import generator.types.TypeAttr;

public record TypePkg<T extends TypeAttr.GenerationType & TypeAttr.NamedType & TypeAttr.TypeRefer>
        (T type, PackagePath packagePath) {
}
