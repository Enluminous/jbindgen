package generator.generation.generator;

import generator.Dependency;
import generator.PackagePath;
import generator.Utils;
import generator.generation.ConstValues;
import generator.types.TypeAttr;

import java.util.List;

public class ConstGenerator implements Generator {

    private final ConstValues constValues;
    private final PackagePath path;
    private final List<ConstValues.Value> values;
    private final Dependency dependency;

    public ConstGenerator(ConstValues constValues, PackagePath path, List<ConstValues.Value> values, Dependency dependency) {
        this.constValues = constValues;
        this.path = path;
        this.values = values;
        this.dependency = dependency;
    }

    @Override
    public void generate() {
        StringBuilder core = new StringBuilder();
        for (var val : values) {
            String typeName = ((TypeAttr.NamedType) val.type()).typeName(TypeAttr.NameType.RAW);
            core.append("""
                        public static final %s %s = new %s(%s);
                    """.formatted(typeName, val.name(), typeName, val.value()));
        }

        if (!core.isEmpty()) {
            String out = path.makePackage();
            out += Generator.extractImports(constValues, dependency);
            out += """
                    public class %s {
                    %s
                    }
                    """.formatted(path.getClassName(), core.toString());
            Utils.write(path, out);
        }
    }
}
