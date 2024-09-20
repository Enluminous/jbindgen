package generator;

import generator.types.CommonTypes;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utils {
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

    public static String makeMemoryLayout(long bytes) {
        return "MemoryLayout.structLayout(MemoryLayout.sequenceLayout(" + bytes + ", "
                + CommonTypes.Primitives.JAVA_BYTE.getMemoryLayout() + "))";
    }
}