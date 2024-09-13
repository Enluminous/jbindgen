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
        return null;
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


    public static Union findRootUnion(Type type) {
        if (type instanceof Elaborated e) {
            return findRootUnion(e.getTarget());
        }
        if (type instanceof TypeDef d) {
            return findRootUnion(d.getTarget());
        }
        if (type instanceof Union u)
            return u;
        return null;
    }


    public static Enum findRootEnum(Type type) {
        if (type instanceof Elaborated e) {
            return findRootEnum(e.getTarget());
        }
        if (type instanceof TypeDef d) {
            return findRootEnum(d.getTarget());
        }
        if (type instanceof Enum e)
            return e;
        return null;
    }

    public static boolean isPrimitiveType(Type t) {
        return findRootPrimitive(t) != null;
    }

    public sealed interface ImplementType permits Mapping, UnsupportedVoid, FakePrimitive {
    }

    public record Mapping(String sharedValueClass, String sharedValueBasicClass,
                          String sharedValueListClass, String objType,
                          String valueLayout, String byteSize) implements ImplementType {
        public Mapping(String objType, String sharedValue, String valueLayout, String byteSize) {
            this(sharedValue, sharedValue + "Basic", sharedValue + "List",
                    objType, valueLayout, byteSize);
        }
    }

    public record UnsupportedVoid() implements ImplementType {

    }

    public record FakePrimitive() implements ImplementType {

    }

    private static final HashMap<String, ImplementType> typeMappings = new HashMap<>();
    private static final HashSet<ImplementType> mappings = new HashSet<>();

    public static HashSet<ImplementType> getMappings() {
        return mappings;
    }

    public static HashMap<String, ImplementType> getTypeMappings() {
        return typeMappings;
    }

    private static void addMapping(List<String> alias, ImplementType mapping) {
        mappings.add(mapping);
        for (String s : alias) {
            typeMappings.put(s, mapping);
        }
    }

    static {
        addMapping(List.of("char", "signed char", "unsigned char", "const char", "const unsigned short"),
                new Mapping("Byte", "VI8", "JAVA_BYTE", "1"));
        addMapping(List.of("short", "signed short", "unsigned short"),
                new Mapping("Short", "VI16", "JAVA_SHORT", "2"));
        addMapping(List.of("int", "signed int", "unsigned int", "const int", "volatile int"),
                new Mapping("Integer", "VI32", "JAVA_INT", "4"));
        addMapping(List.of("long", "signed long", "unsigned long", "unsigned long long", "long long"),
                new Mapping("Long", "VI64", "JAVA_LONG", "8"));

        addMapping(List.of("float", "const float"),
                new Mapping("Float", "VFP32", "JAVA_FLOAT", "4"));
        addMapping(List.of("double"),
                new Mapping("Double", "VFP64", "JAVA_DOUBLE", "8"));

        addMapping(List.of(),
                new Mapping("MemorySegment", "VPointer", "ADDRESS", "8"));

        addMapping(List.of("long double"), new FakePrimitive());

        addMapping(List.of("void", "const void"), new UnsupportedVoid());
    }
}