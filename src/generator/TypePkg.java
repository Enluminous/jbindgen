package generator;

import generator.types.TypeAttr;

public record TypePkg<T extends TypeAttr.GenerationType>(T type, PackagePath packagePath) {
}
