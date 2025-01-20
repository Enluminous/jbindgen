package generator;

import generator.types.GenerationTypeHolder;
import generator.types.TypeAttr;

public record TypePkg<T extends TypeAttr.GenerationType>(GenerationTypeHolder<T> typeHolder, PackagePath packagePath) {
    public T type() {
        return typeHolder.getGenerationType();
    }
}
