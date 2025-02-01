package generator.generation.generator;

import generator.PackagePath;
import generator.Utils;
import generator.generation.ConstValues;
import generator.types.TypeAttr;

import java.util.List;

public class ConstGenerator implements Generator {

    private final PackagePath path;
    private final List<ConstValues.Value> values;

    public ConstGenerator(PackagePath path, List<ConstValues.Value> values) {
        this.path = path;
        this.values = values;
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
            out += """
                    public class %s {
                    %s
                    }
                    """.formatted(path.getClassName(), core.toString());
            Utils.write(path.getFilePath(), out);
        }
    }
}
