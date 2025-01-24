package generator.generation.generator;

import analyser.Analyser;
import generator.Dependency;
import generator.PackagePath;
import generator.Utils;

import java.util.HashSet;

public class MacroGenerator implements Generator {
    private final PackagePath packagePath;
    private final HashSet<Analyser.Macro> macros;

    public MacroGenerator(PackagePath packagePath, HashSet<Analyser.Macro> macros, Dependency dependency) {
        this.packagePath = packagePath;
        this.macros = macros;
    }

    @Override
    public void generate() {
        StringBuilder core = new StringBuilder();
        for (Analyser.Macro macro : macros) {
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
        Utils.write(packagePath.getFilePath(), out);
    }
}
