package analyser;

import libclang.common.Str;

public record Macro(PrimitiveTypes type, String declName, String initializer, String comment) {
}
