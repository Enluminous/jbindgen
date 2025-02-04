package generator;

import generator.types.CommonTypes;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.regex.Pattern;

public class Utils {
    private static final Pattern CLASS_NAME = Pattern.compile("^[a-zA-Z0-9_$]*$");
    private static final Pattern VAR_NAME = Pattern.compile("^[a-zA-Z_$][a-zA-Z\\d_$]*$");

    private static final HashSet<Path> writingPaths = new HashSet<>();

    public static void write(Path path, String content) {
        try {
            Files.createDirectories(path.getParent());
            if (writingPaths.contains(path.toAbsolutePath())) {
                throw new RuntimeException("Path " + path.toAbsolutePath() + " already written");
            }
            writingPaths.add(path.toAbsolutePath());
            FileWriter fileWriter = new FileWriter(path.toFile(), false);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String makeMemoryLayout(long bytes) {
        return "MemoryLayout.structLayout(MemoryLayout.sequenceLayout(" + bytes + ", "
                + CommonTypes.Primitives.JAVA_BYTE.getMemoryLayout() + "))";
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