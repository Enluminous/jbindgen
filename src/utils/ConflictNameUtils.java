package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConflictNameUtils {
    private static List<String> getForbidNames() {
        var JAVA_KEY_WORDS = List.of("abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
                "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
                "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
                "interface", "long", "native", "new", "package", "private", "protected", "public",
                "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
                "throw", "throws", "transient", "try", "void", "volatile", "while",
                "true", "false", "null");
        var JAVA_METHODS = List.of("clone", "toString", "finalize", "hashCode", "getClass", "notify", "wait", "operator");
        ArrayList<String> constBlack = new ArrayList<>();
        constBlack.addAll(JAVA_KEY_WORDS);
        constBlack.addAll(JAVA_METHODS);
        return Collections.unmodifiableList(constBlack);
    }

    private static final List<String> FORBIDDEN_NAMES = getForbidNames();

    public static String getNonConflictsName(String input, List<String> forbidNames, List<String> existingNames) {
        if (forbidNames.contains(input)) {
            do {
                input += "$";
            } while (existingNames.contains(input));
        }
        return input;
    }

    public static String getNonConflictsNameExt(String input, List<String> ext, List<String> existingNames) {
        List<String> forbidNames = new ArrayList<>(FORBIDDEN_NAMES);
        forbidNames.addAll(ext);
        return getNonConflictsName(input, forbidNames, existingNames);
    }
}
