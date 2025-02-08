package preprocessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import analyser.PrimitiveTypes;
import generator.PackagePath;
import generator.generation.AbstractGeneration;
import generator.generation.ArrayNamed;
import generator.generation.Common;
import generator.generation.ConstValues;
import generator.generation.Enumerate;
import generator.generation.FuncPointer;
import generator.generation.FuncSymbols;
import generator.generation.Generation;
import generator.generation.Macros;
import generator.generation.RefOnly;
import generator.generation.Structure;
import generator.generation.SymbolProvider;
import generator.generation.ValueBased;
import generator.generation.VarSymbols;
import generator.generation.VoidBased;
import generator.types.CommonTypes;

public class Utils {
    public static CommonTypes.BindTypes conv2BindTypes(PrimitiveTypes.CType type) {
        switch (type) {
            case C_I8 -> {
                return CommonTypes.BindTypes.I8;
            }
            case C_I16 -> {
                return CommonTypes.BindTypes.I16;
            }
            case C_I32 -> {
                return CommonTypes.BindTypes.I32;
            }
            case C_I64 -> {
                return CommonTypes.BindTypes.I64;
            }
            case C_FP32 -> {
                return CommonTypes.BindTypes.FP32;
            }
            case C_FP64 -> {
                return CommonTypes.BindTypes.FP64;
            }
            default -> {
                throw new RuntimeException();
            }
        }
    }

    static List<String> getForbidNames() {
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

    public interface DestinationProvider {
        Destination symbolProvider();

        Destination macros();

        Destination constants();

        Destination funcSymbols();

        PathOnly common();

        PathOnly enumerate();

        PathOnly valueBased();

        PathOnly struct();

        PathOnly funcProtocol();

        PathOnly refOnly();

        PathOnly voidBased();

        PathOnly arrayNamed();

        static DestinationProvider ofDefault(PackagePath p, String libName) {
            return new DefaultDestinationProvider(p, libName);
        }
    }

    public interface Filter extends Predicate<Map.Entry<Generation<?>, Optional<String>>> {
        @Override
        default boolean test(Map.Entry<Generation<?>, Optional<String>> entry) {
            Generation<?> generation = entry.getKey();
            Optional<String> value = entry.getValue();

            return switch (generation) {
                case AbstractGeneration<?> abstractGeneration -> switch (abstractGeneration) {
                    case Common common -> testCommon(value);
                    case ArrayNamed arrayNamed -> testArrayNamed(value);
                    case Enumerate enumerate -> testEnumerate(value);
                    case FuncPointer funcPointer -> testFuncPointer(value);
                    case RefOnly refOnly -> testRefOnly(value);
                    case Structure structure -> testStructure(value);
                    case SymbolProvider symbolProvider -> testSymbolProvider(value);
                    case ValueBased valueBased -> testValueBased(value);
                    case VoidBased voidBased -> testVoidBased(value);
                };
                case ConstValues constValues -> testConstValues(value);
                case FuncSymbols funcSymbols -> testFuncSymbols(value);
                case Macros macros -> testMacros(value);
                case VarSymbols varSymbols -> testVarSymbols(value);
            };
        }

        default boolean testCommon(Optional<String> value) {
            return true;
        }

        default boolean testArrayNamed(Optional<String> value) {
            return true;
        }

        default boolean testEnumerate(Optional<String> value) {
            return true;
        }

        default boolean testFuncPointer(Optional<String> value) {
            return true;
        }

        default boolean testRefOnly(Optional<String> value) {
            return true;
        }

        default boolean testStructure(Optional<String> value) {
            return true;
        }

        default boolean testSymbolProvider(Optional<String> value) {
            return true;
        }

        default boolean testValueBased(Optional<String> value) {
            return true;
        }

        default boolean testVoidBased(Optional<String> value) {
            return true;
        }

        default boolean testConstValues(Optional<String> value) {
            return true;
        }

        default boolean testFuncSymbols(Optional<String> value) {
            return true;
        }

        default boolean testMacros(Optional<String> value) {
            return true;
        }

        default boolean testVarSymbols(Optional<String> value) {
            return true;
        }

        static Filter ofDefault(java.util.function.Function<String, Boolean> test) {
            return new Filter() {
                final Predicate<Optional<String>> filter =
                        value -> value.map(test).orElse(true);

                @Override
                public boolean testArrayNamed(Optional<String> value) {
                    return filter.test(value);
                }

                @Override
                public boolean testEnumerate(Optional<String> value) {
                    return filter.test(value);
                }

                @Override
                public boolean testFuncPointer(Optional<String> value) {
                    return filter.test(value);
                }

                @Override
                public boolean testRefOnly(Optional<String> value) {
                    return filter.test(value);
                }

                @Override
                public boolean testStructure(Optional<String> value) {
                    return filter.test(value);
                }

                @Override
                public boolean testValueBased(Optional<String> value) {
                    return filter.test(value);
                }
            };
        }
    }

    public record Destination(PackagePath path) {
        public Destination {
            path.reqClassName();
        }
    }

    public record PathOnly(PackagePath path) {
        public PathOnly {
            path.reqNonClassName();
        }
    }

    public static class DefaultDestinationProvider implements DestinationProvider {
        private final PackagePath p;
        private final String libName;

        public DefaultDestinationProvider(PackagePath p, String libName) {
            this.p = p;
            this.libName = libName;
        }

        @Override
        public Destination symbolProvider() {
            return new Destination(p.end(libName + "SymbolProvider"));
        }

        @Override
        public Destination macros() {
            return new Destination(p.end(libName + "Macros"));
        }

        @Override
        public Destination constants() {
            return new Destination(p.end(libName + "Constants"));
        }

        @Override
        public Destination funcSymbols() {
            return new Destination(p.end(libName + "FunctionSymbols"));
        }

        @Override
        public PathOnly common() {
            return new PathOnly(p.add("common"));
        }

        @Override
        public PathOnly enumerate() {
            return new PathOnly(p.add("enumerates"));
        }

        @Override
        public PathOnly valueBased() {
            return new PathOnly(p.add("values"));
        }

        @Override
        public PathOnly struct() {
            return new PathOnly(p.add("structs"));
        }

        @Override
        public PathOnly funcProtocol() {
            return new PathOnly(p.add("functions"));
        }

        @Override
        public PathOnly refOnly() {
            return new PathOnly(p.add("opaques"));
        }

        @Override
        public PathOnly voidBased() {
            return new PathOnly(p.add("opaques"));
        }

        @Override
        public PathOnly arrayNamed() {
            return new PathOnly(p.add("structs"));
        }
    }
}
