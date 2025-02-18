package generator;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.regex.Pattern;

public class Utils {
    private static final Pattern CLASS_NAME = Pattern.compile("^[a-zA-Z0-9_$]*$");
    private static final Pattern VAR_NAME = Pattern.compile("^[a-zA-Z_$][a-zA-Z\\d_$]*$");

    private static final HashSet<PackagePath> WRITING_PATHS = new HashSet<>();

    public static void write(PackagePath path, String content) {
        if (WRITING_PATHS.contains(path)) {
            throw new RuntimeException("Path " + path.getFilePath() + " already written");
        }
        WRITING_PATHS.add(path);
        write(path.getFilePath(), content);
    }

    public static void write(Path path, String content) {
        try {
            Files.createDirectories(path.getParent());
            FileWriter fileWriter = new FileWriter(path.toFile(), false);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isValidClassName(String className) {
        if (className == null || className.isBlank()) {
            return false;
        }
        return CLASS_NAME.matcher(className).matches();
    }

    public static boolean isValidVarName(String varName) {
        if (varName == null || varName.isBlank()) {
            return false;
        }
        return VAR_NAME.matcher(varName).matches();
    }
}