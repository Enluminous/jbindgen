package generator;

import analyser.types.*;
import analyser.types.Enum;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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


    public static Primitive findRootPrimitive(Type type) {
        if (type instanceof Elaborated e) {
            return findRootPrimitive(e.getTarget());
        }
        if (type instanceof TypeDef d) {
            return findRootPrimitive(d.getTarget());
        }
        if (type instanceof Primitive p)
            return p;
        throw new RuntimeException();
    }

    public static Struct findRootStruct(Type type) {
        if (type instanceof Elaborated e) {
            return findRootStruct(e.getTarget());
        }
        if (type instanceof TypeDef d) {
            return findRootStruct(d.getTarget());
        }
        if (type instanceof Struct p)
            return p;
        return null;
    }

    public static boolean isPrimitiveType(Type t) {
        switch (t) {
            case Array a -> {
                return false;
            }
            case Elaborated e -> {
                return isPrimitiveType(e.getTarget());
            }
            case Enum e -> {
                return false;
            }
            case Pointer p -> {
                return false;
            }
            case Struct s -> {
                return false;
            }
            case TypeDef d -> {
                return isPrimitiveType(d.getTarget());
            }
            case TypeFunction f -> {
                return false;
            }
            case Union u -> {
                return false;
            }
            case Primitive p -> {
                return true;
            }
            default -> throw new IllegalStateException("Unexpected value: " + t);
        }
    }

    public record Mapping(String sharedValueClass, String sharedValueBasicClass,
                          String sharedValueListClass, String objType,
                          String valueLayout, String byteSize) {
        public Mapping(String objType, String sharedValue, String valueLayout, String byteSize) {
            this(sharedValue, sharedValue + "Basic", sharedValue + "List",
                    objType, valueLayout, byteSize);
        }
    }

    private static final HashMap<String, Mapping> typeMappings = new HashMap<>();
    private static final HashSet<Mapping> mappings = new HashSet<>();

    public static HashSet<Mapping> getMappings() {
        return mappings;
    }

    public static HashMap<String, Mapping> getTypeMappings() {
        return typeMappings;
    }

    private static void addMapping(List<String> alias, Mapping mapping) {
        mappings.add(mapping);
        for (String s : alias) {
            typeMappings.put(s, mapping);
        }
    }

    static {
        addMapping(List.of("char", "signed char", "unsigned char"),
                new Mapping("Byte", "VI8", "JAVA_BYTE", "1"));
        addMapping(List.of("short", "signed short", "unsigned short"),
                new Mapping("Short", "VI16", "JAVA_SHORT", "2"));
        addMapping(List.of("int", "signed int", "unsigned int", "int"),
                new Mapping("Integer", "VI32", "JAVA_INT", "4"));
        addMapping(List.of("long", "signed long", "unsigned long", "unsigned long long", "long long"),
                new Mapping("Long", "VI64", "JAVA_LONG", "8"));

        addMapping(List.of("float"),
                new Mapping("Float", "VFP32", "JAVA_FLOAT", "4"));
        addMapping(List.of("double"),
                new Mapping("Double", "VFP64", "JAVA_DOUBLE", "8"));

        addMapping(List.of(),
                new Mapping("MemorySegment", "VPointer", "ADDRESS", "8"));
    }
}