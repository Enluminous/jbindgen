package generator;

import generator.types.TypeAttr;

public record TypePkg<T extends TypeAttr.Type>(T type, PackagePath packagePath) {
}
