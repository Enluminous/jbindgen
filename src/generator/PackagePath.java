package generator;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

import static utils.CommonUtils.Assert;

public class PackagePath {
    private final ArrayList<String> packages = new ArrayList<>();
    private final Path root;
    private final String className;

    public PackagePath(Path root) {
        this.root = root;
        className = null;
    }

    public PackagePath() {
        root = null;
        className = null;
    }

    private PackagePath(Path root, ArrayList<String> packages, String className) {
        this.root = root;
        this.packages.addAll(packages);
        this.className = className;
    }

    public PackagePath add(String packageName) {
        reqNonClassName();
        var pkg = new ArrayList<>(packages);
        pkg.add(packageName);
        return new PackagePath(root, pkg, className);
    }

    private static final Pattern CLASS_NAME = Pattern.compile("^[a-zA-Z0-9_$]*$");

    private static boolean isValidClassName(String className) {
        if (className == null || className.trim().isEmpty()) {
            return false;
        }
        return CLASS_NAME.matcher(className).matches();
    }

    public PackagePath end(String className) {
        Assert(isValidClassName(className), "invalid class name: " + className);
        return new PackagePath(root, packages, className);
    }

    public String makePackage() {
        return "package " + String.join(".", packages) + ";\n";
    }

    public String makeImport() {
        reqClassName();
        return "import " + String.join(".", packages) + "." + className + ";\n";
    }


    public String getClassName() {
        reqClassName();
        return className;
    }

    public Path getFilePath() {
        reqClassName();
        Path path = root;
        for (String p : packages) {
            path = path.resolve(p);
        }
        return path.resolve(className + ".java");
    }

    private void reqClassName() {
        if (className == null) {
            throw new IllegalArgumentException("need class name");
        }
    }

    private void reqNonClassName() {
        if (className != null) {
            throw new IllegalArgumentException("Class " + className + " is already defined, path is end");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PackagePath that)) return false;
        return Objects.equals(packages, that.packages) && Objects.equals(root, that.root) && Objects.equals(className, that.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packages, root, className);
    }
}
