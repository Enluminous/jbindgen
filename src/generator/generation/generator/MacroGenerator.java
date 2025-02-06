package generator.generation.generator;

import analyser.Macro;
import generator.Dependency;
import generator.PackagePath;
import generator.Utils;

import java.util.Set;

public class MacroGenerator implements Generator {
    private final PackagePath packagePath;
    private final Set<Macro> macros;

    public MacroGenerator(PackagePath packagePath, Set<Macro> macros, Dependency dependency) {
        this.packagePath = packagePath;
        this.macros = macros;
    }

    @Override
    public void generate() {
        StringBuilder core = new StringBuilder();
        for (Macro macro : macros) {
            core.append("""
                        public static final %s %s = %s; // %s
                    """.formatted(macro.type(), macro.left(), macro.value(), macro.right()));
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
