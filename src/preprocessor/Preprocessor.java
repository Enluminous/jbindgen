package preprocessor;

import analyser.Declare;
import analyser.Function;
import analyser.Macro;
import analyser.Para;
import analyser.PrimitiveTypes;
import analyser.types.*;
import analyser.types.Enum;
import analyser.types.Record;
import generator.Generator;
import generator.PackagePath;
import generator.TypePkg;
import generator.generation.*;
import generator.generation.generator.MacroGenerator;
import generator.types.*;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static utils.CommonUtils.Assert;

public class Preprocessor {
    private static String getName(String nullAbleName, String name) {
        String ret = nullAbleName;
        if (ret == null)
            ret = name;
        String cutStr = "const ";
        if (ret.startsWith(cutStr)) {
            ret = ret.substring(cutStr.length());
        }
        return ret;
    }

    private final HashMap<String, StructValue> structs = new HashMap<>();

    record StructValue(StructType s, Record r) {
    }

    private ArrayList<StructType.Member> solveMembers(Record record) {
        ArrayList<String> constBlack = getConstBlack();
        List<String> memberBlacks = record.getMembers().stream().map(Para::paraName).toList();
        ArrayList<StructType.Member> members = new ArrayList<>();
        for (Para member : record.getMembers()) {
            String input = member.paraName();
            if (constBlack.contains(input)) {
                input += "$";
                while (memberBlacks.contains(input)) {
                    input += "$";
                }
            }
            long offset = member.offset().orElseThrow();
            long bitSize = member.bitWidth().orElseThrow();
            Assert(offset >= 0);
            Assert(bitSize > 0);
            members.add(new StructType.Member(conv(member.paraType(), null), input,
                    offset, bitSize));
        }
        return members;
    }

    private static ArrayList<String> getConstBlack() {
        var JAVA_KEY_WORDS = List.of("abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
                "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
                "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
                "interface", "long", "native", "new", "package", "private", "protected", "public",
                "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
                "throw", "throws", "transient", "try", "void", "volatile", "while",
                "true", "false", "null");
        var JAVA_METHODS = List.of("clone", "toString", "finalize", "hashCode", "getClass", "notify", "wait",
                "value", "reinterpretSize");
        ArrayList<String> constBlack = new ArrayList<>();
        constBlack.addAll(JAVA_KEY_WORDS);
        constBlack.addAll(JAVA_METHODS);
        return constBlack;
    }

    private StructType getStruct(long byteSize, String typeName, Record record) {
        if (structs.containsKey(typeName)) {
            if (!Objects.equals(record, structs.get(typeName).r)) {
                throw new RuntimeException();
            }
            return structs.get(typeName).s;
        }

        return new StructType(byteSize, typeName, structType -> {
            structs.put(typeName, new StructValue(structType, record));
            return solveMembers(record);
        });
    }

    private FunctionPtrType getTypeFunction(String typeName, TypeAttr.TypeRefer retType, TypeFunction typeFunction) {
        ArrayList<FunctionPtrType.Arg> args = new ArrayList<>();
        ArrayList<Para> paras = typeFunction.getParas();
        for (int i = 0; i < paras.size(); i++) {
            Para para = paras.get(i);
            String paraName = para.paraName();
            if (paraName == null || paraName.isEmpty()) {
                paraName = "arg" + i;
            }
            args.add(new FunctionPtrType.Arg(paraName, conv(para.paraType(), null)));
        }
        return new FunctionPtrType(typeName, args, retType);
    }

    private Type typedefLookUp(Type type) {
        switch (type) {
            case Array array -> {
                return array;
            }
            case Enum anEnum -> {
                return anEnum;
            }
            case Pointer pointer -> {
                return pointer;
            }
            case Primitive primitive -> {
                return primitive;
            }
            case Record record -> {
                return record;
            }
            case TypeDef typeDef -> {
                return typedefLookUp(typeDef.getTarget());
            }
            case TypeFunction typeFunction -> {
                return typeFunction;
            }
        }
    }

    /**
     * conv analyser's type to generator's
     *
     * @param type analyser type
     * @param name type name, null means refer to type's displayName, normally be used for TypeDef->Primitive, Typedef->Pointer
     *             must be specified for single level
     * @return converted type
     */
    private TypeAttr.TypeRefer conv(Type type, String name) {
        switch (type) {
            case Array array -> {
                if (name != null) {
                    return new ArrayTypeNamed(name, array.getElementCount(), conv(array.getElementType(), null), array.getSizeof());
                }
                return new ArrayType(array.getElementCount(), conv(array.getElementType(), null), array.getSizeof());
            }
            case Enum anEnum -> {
                TypeAttr.TypeRefer conv = conv(anEnum.getDeclares().getFirst().type(), null);
                var bindTypes = (CommonTypes.BindTypes) conv;
                List<EnumType.Member> members = new ArrayList<>();
                for (Declare declare : anEnum.getDeclares()) {
                    members.add(new EnumType.Member(Long.parseLong(declare.value()), declare.name()));
                }
                return new EnumType(bindTypes, anEnum.getDisplayName(), members);
            }
            case Pointer pointer -> {
                if (typedefLookUp(pointer.getTarget()) instanceof TypeFunction f) {
                    // typedef void (*callback)(int a, int b);
                    // void accept(callback ptr);
                    // ptr is the pointer of callback.
                    return getTypeFunction(getName(name, f.getDisplayName()), conv(f.getRet(), null), f);
                }

                // name != null means comes from typedef
                PointerType t = new PointerType(conv(pointer.getTarget(), null));
                if (name != null)
                    return new ValueBasedType(name, t);
                return t;
            }
            case Primitive primitive -> {
                PrimitiveTypes.CType primitiveType = primitive.getPrimitiveType();
                if (primitiveType == PrimitiveTypes.CType.C_VOID) {
                    return name == null ? VoidType.VOID : new VoidType(name);
                }
                CommonTypes.BindTypes bindTypes = Utils.conv2BindTypes(primitiveType);
                if (bindTypes == null)
                    throw new RuntimeException();
                Assert(bindTypes.getPrimitiveType().getByteSize() == primitive.getSizeof(), "Unhandled Data Model");
                if (name == null) {
                    // primitive type
                    return bindTypes;
                }
                // name != null means comes from typedef
                return new ValueBasedType(name, bindTypes);
            }
            case Record record -> {
                String typeName = getName(name, record.getDisplayName());
                if (record.isIncomplete())
                    return new RefOnlyType(typeName);
                return getStruct(record.getSizeof(), typeName, record);
            }
            case TypeDef typeDef -> {
                return conv(typeDef.getTarget(), getName(name, typeDef.getDisplayName()));
            }
            case TypeFunction typeFunction -> {
                // typedef void (callback)(int a, int b);
                // void accept(callback ptr);
                // ptr IS the pointer of callback, can be converted to the FunctionPtrType safely.
                return getTypeFunction(getName(name, typeFunction.getDisplayName()), conv(typeFunction.getRet(), null), typeFunction);
            }
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

    public Preprocessor(List<Function> functions, HashSet<Macro> macros, ArrayList<Declare> varDeclares,
                        HashMap<String, Type> types, DestinationProvider dest, Filter filter) {
        record Generations(HashMap<Generation<?>, Optional<String>> genMap) {

            Generations() {
                this(new HashMap<>());
            }

            public void add(Generation<?> generation) {
                genMap.put(generation, Optional.empty());
            }

            public void add(Generation<?> generation, String location) {
                genMap.put(generation, Optional.ofNullable(location));
            }

            public void addAll(Collection<? extends Generation<?>> generation) {
                genMap.putAll(generation.stream().collect(Collectors.toMap(k -> k, v -> Optional.empty())));
            }

            void forEach(BiConsumer<? super Generation<?>, ? super Optional<String>> fun) {
                genMap.forEach(fun);
            }

            Set<Generation<?>> toGenerations(Predicate<Map.Entry<Generation<?>, Optional<String>>> filter) {
                return genMap.entrySet().stream().filter(filter).map(Map.Entry::getKey).collect(Collectors.toSet());
            }
        }

        Generations generations = new Generations();
        ArrayList<ConstValues.Value> constValues = new ArrayList<>(varDeclares.stream()
                .map(d -> new ConstValues.Value(conv(d.type(), null), d.value(), d.name())).toList());

        // types
        for (Type s : types.values()) {
            TypeAttr.TypeRefer conv = conv(s, null);
            switch (conv) {
                case ArrayTypeNamed arrayTypeNamed ->
                        generations.add(new ArrayNamed(dest.arrayNamed().path(), arrayTypeNamed));
                case EnumType e -> {
                    Type type = typedefLookUp(s);
                    Enum en = (Enum) type;
                    if (en.isUnnamed()) {
                        for (Declare declare : en.getDeclares()) {
                            constValues.add(new ConstValues.Value(conv(declare.type(), null), declare.value(), declare.name()));
                        }
                    } else
                        generations.add(new Enumerate(dest.enumerate().path(), e));
                }
                case FunctionPtrType functionPtrType ->
                        generations.add(new FuncPointer(dest.funcProtocol().path(), functionPtrType));
                case ValueBasedType valueBasedType ->
                        generations.add(new ValueBased(dest.valueBased().path(), valueBasedType));
                case VoidType voidType ->
                        generations.add(new VoidBased(dest.voidBased().path(), voidType));
                case RefOnlyType refOnlyType ->
                        generations.add(new RefOnly(dest.refOnly().path(), refOnlyType));
                case StructType structType ->
                        generations.add(new Structure(dest.struct().path(), structType));
                case CommonTypes.BindTypes _, PointerType _, ArrayType _ -> {
                }
                default -> throw new IllegalStateException("Unexpected value: " + conv);
            }
        }
        // constants
        generations.add(new ConstValues(dest.constants().path(), constValues));

        // common
        generations.addAll(Common.makeBindTypes(dest.common().path()));
        generations.addAll(Common.makeValueInterfaces(dest.common().path()));
        generations.addAll(Common.makeFFMs());
        generations.addAll(Common.makeBindTypeInterface(dest.common().path()));
        generations.addAll(Common.makeBasicOperations(dest.common().path()));
        generations.addAll(Common.makeSpecific(dest.common().path()));

        // macros
        HashSet<MacroGenerator.Macro> macro = new HashSet<>();
        macros.forEach(e -> {
            PrimitiveTypes type = e.type();
            if (type instanceof PrimitiveTypes.CType cType)
                macro.add(new MacroGenerator.Macro(Utils.conv2BindTypes(cType).getPrimitiveType(), e.declName(), e.initializer(), e.comment()));
        });
        generations.add(new Macros(dest.macros().path(), macro));

        // symbol provider
        SymbolProviderType provider = new SymbolProviderType(dest.symbolProvider().path().getClassName());
        generations.add(new SymbolProvider(dest.symbolProvider().path.removeEnd(), provider));

        // function symbols
        ArrayList<FunctionPtrType> functionPtrTypes = new ArrayList<>();
        for (Function function : functions) {
            List<FunctionPtrType.Arg> args = function.paras().stream()
                    .map(para -> new FunctionPtrType.Arg(para.paraName(), conv(para.paraType(), null))).toList();
            functionPtrTypes.add(new FunctionPtrType(function.name(), args, conv(function.ret(), null)));
        }
        generations.add(new FuncSymbols(dest.funcSymbols().path(), functionPtrTypes, provider));

        // GenerationTypes
        HashMap<TypeAttr.GenerationType, Generation<?>> depGen = new HashMap<>();
        generations.forEach((g, _) -> g.getImplTypes().stream()
                .map(TypePkg::type).forEach(o -> depGen.put(o, g)));

        Generator generator = new Generator(generations.toGenerations(filter), depGen::get);
        generator.generate();
    }
}