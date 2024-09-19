package generator.config;

import java.nio.file.Path;
import java.util.ArrayList;

public class PackagePath {
    private final ArrayList<String> packages = new ArrayList<>();
    private final Path root;
    private final String className;

    public PackagePath(Path root) {
        this.root = root;
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

    public PackagePath end(String className) {
        return new PackagePath(root, packages, className);
    }

    public String getPackage() {
        return String.join(".", packages);
    }

    public String getImport() {
        reqClassName();
        return getPackage() + "." + className;
    }


    public String getClassName() {
        reqClassName();
        return className;
    }

    public Path getPath() {
        Path path = root;
        for (String p : packages) {
            path = path.resolve(p);
        }
        return path;
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
}
