package preprocessor;

import analyser.Analyser;
import analyser.types.*;
import analyser.types.Enum;
import generator.types.EnumType;
import generator.types.TypeAttr;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Utils {
    public static Primitive findRootPrimitive(Type type) {
        if (type instanceof TypeDef d) {
            return findRootPrimitive(d.getTarget());
        }
        if (type instanceof Primitive p)
            return p;
        return null;
    }

    public static Struct findRootStruct(Type type) {
        if (type instanceof TypeDef d) {
            return findRootStruct(d.getTarget());
        }
        if (type instanceof Struct p)
            return p;
        return null;
    }


    public static Union findRootUnion(Type type) {
        if (type instanceof TypeDef d) {
            return findRootUnion(d.getTarget());
        }
        if (type instanceof Union u)
            return u;
        return null;
    }

    public static analyser.types.Enum findRootEnum(Type type) {
        if (type instanceof TypeDef d) {
            return findRootEnum(d.getTarget());
        }
        if (type instanceof Enum e)
            return e;
        return null;
    }

    public static TypeFunction findRootTypedFunc(Type type) {
        if (type instanceof TypeDef d) {
            return findRootTypedFunc(d.getTarget());
        }
        if (type instanceof Pointer p) {
            return findRootTypedFunc(p.getTarget());
        }
        if (type instanceof TypeFunction f)
            return f;
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
}
