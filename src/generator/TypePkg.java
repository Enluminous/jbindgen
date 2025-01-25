package generator;

import generator.types.Holder;
import generator.types.TypeAttr;

public record TypePkg<T extends TypeAttr.GenerationType>(Holder<T> typeHolder, PackagePath packagePath) {
    public T type() {
        return typeHolder.getT();
    }
}
