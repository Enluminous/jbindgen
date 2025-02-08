package generator.generation.generator;

import java.util.Set;

import generator.Dependency;
import generator.PackagePath;
import generator.Utils;
import generator.generation.Macros;

public class MacroGenerator implements Generator {
    private final PackagePath packagePath;

    private final Set<Macros.Macro> macros;

    public MacroGenerator(PackagePath packagePath, Set<Macros.Macro> macros, Dependency dependency) {
        this.packagePath = packagePath;
        this.macros = macros;
    }

    @Override
    public void generate() {
        StringBuilder core = new StringBuilder();
        for (var macro : macros) {
            switch (macro) {
                case Macros.Primitive p -> core.append("""
                            public static final %s %s = %s; // %s
                        """.formatted(p.primitives().getPrimitiveTypeName(), p.declName(), p.initializer(), p.comment()));
                case Macros.StrMacro s -> core.append("""
                            public static final String %s = %s; // %s
                        """.formatted(s.declName(), s.initializer(), s.comment()));
            }

        }
        String out = packagePath.makePackage();
        out += """
                public class %s {
                %s
                }
                """.formatted(packagePath.getClassName(), core.toString());
        Utils.write(packagePath, out);
    }
}
