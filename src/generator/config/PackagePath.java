package generator.config;

import java.nio.file.Path;
import java.util.ArrayList;

public class PackagePath {
    private final ArrayList<String> packages = new ArrayList<>();
    private final Path root;

    public PackagePath(Path root) {
        this.root = root;
    }

    public PackagePath(PackagePath packagePath, String subPkg) {
        this.packages.addAll(packagePath.packages);
        this.packages.add(subPkg);
        this.root = packagePath.root;
    }

    public PackagePath add(String packageName) {
        return new PackagePath(this, packageName);
    }

    public String getPackage() {
        return String.join(".", packages);
    }

    public Path getPath() {
        Path path = root;
        for (String p : packages) {
            path = path.resolve(p);
        }
        return path;
    }
}
