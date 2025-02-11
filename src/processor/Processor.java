package processor;

import analyser.*;
import analyser.types.Enum;
import analyser.types.Type;
import generator.TypePkg;
import generator.generation.*;
import generator.types.*;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Processor {
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

        HashMap<TypeAttr.GenerationType, Generation<?>> getTypeGenerations() {
            HashMap<TypeAttr.GenerationType, Generation<?>> depGen = new HashMap<>();
            genMap.forEach((g, _) -> g.getImplTypes().stream()
                    .map(TypePkg::type).forEach(o -> depGen.put(o, g)));
            return depGen;
        }
    }

    private final HashMap<String, Type> processedTypes = new HashMap<>();
    private final HashMap<TypeAttr.GenerationType, Generation<?>> allTypes = new HashMap<>();
    private final HashSet<Generation<?>> mustGenerate = new HashSet<>();

    public Processor(Analyser analyser, Utils.DestinationProvider dest, Utils.Filter filter) {
        this(analyser.getFunctions(), analyser.getMacros(), analyser.getVarDeclares(), analyser.getTypes(), dest, filter);
    }

    public Processor(List<Function> functions, HashSet<Macro> macros, ArrayList<Declare> varDeclares,
                     HashMap<String, Type> types, Utils.DestinationProvider dest, Utils.Filter filter) {
        Generations generations = new Generations();
        // common
        generations.addAll(Common.makeBindTypes(dest.common().path()));
        generations.addAll(Common.makeValueInterfaces(dest.common().path()));
        generations.addAll(Common.makeFFMs());
        generations.addAll(Common.makeBindTypeInterface(dest.common().path()));
        generations.addAll(Common.makeBasicOperations(dest.common().path()));
        generations.addAll(Common.makeSpecific(dest.common().path()));
        processType(generations, functions, macros, varDeclares, types, dest, Collections.unmodifiableMap(processedTypes));
        processedTypes.putAll(types);
        allTypes.putAll(generations.getTypeGenerations());
        mustGenerate.addAll(generations.toGenerations(filter));
    }

    private static void processType(Generations generations, List<Function> functions, HashSet<Macro> macros, ArrayList<Declare> varDeclares, HashMap<String, Type> types, Utils.DestinationProvider dest, Map<String, Type> processedTypes) {
        ArrayList<ConstValues.Value> constValues = new ArrayList<>(varDeclares.stream()
                .map(d -> new ConstValues.Value(Utils.conv(d.type(), null), d.value(), d.name())).toList());
        // types
        for (var t : types.entrySet()) {
            if (processedTypes.containsKey(t.getKey())) {
                continue;
            }
            Type s = t.getValue();
            TypeAttr.TypeRefer conv = Utils.conv(s, null);
            switch (conv) {
                case ArrayTypeNamed arrayTypeNamed ->
                        generations.add(new ArrayNamed(dest.arrayNamed().path(), arrayTypeNamed));
                case EnumType e -> {
                    Type type = Utils.typedefLookUp(s);
                    Enum en = (Enum) type;
                    if (en.isUnnamed()) {
                        for (Declare declare : en.getDeclares()) {
                            constValues.add(new ConstValues.Value(Utils.conv(declare.type(), null), declare.value(), declare.name()));
                        }
                    } else
                        generations.add(new Enumerate(dest.enumerate().path(), e));
                }
                case FunctionPtrType functionPtrType ->
                        generations.add(new FuncPointer(dest.funcProtocol().path(), functionPtrType));
                case ValueBasedType valueBasedType ->
                        generations.add(new ValueBased(dest.valueBased().path(), valueBasedType));
                case VoidType voidType -> generations.add(new VoidBased(dest.voidBased().path(), voidType));
                case RefOnlyType refOnlyType -> generations.add(new RefOnly(dest.refOnly().path(), refOnlyType));
                case StructType structType -> generations.add(new Structure(dest.struct().path(), structType));
                case CommonTypes.BindTypes _, PointerType _, ArrayType _ -> {
                }
                default -> throw new IllegalStateException("Unexpected value: " + conv);
            }
        }
        // constants
        generations.add(new ConstValues(dest.constants().path(), constValues));
        // macros
        HashSet<Macros.Macro> macro = new HashSet<>();
        macros.forEach(e -> {
            switch (e.type()) {
                case PrimitiveTypes.CType c ->
                        macro.add(new Macros.Primitive(Utils.conv2BindTypes(c).getPrimitiveType(), e.declName(), e.initializer(), e.comment()));
                case PrimitiveTypes.JType _ ->
                        macro.add(new Macros.StrMacro(e.declName(), e.initializer(), e.comment()));
            }
        });
        generations.add(new Macros(dest.macros().path(), macro));

        // symbol provider
        SymbolProviderType provider = new SymbolProviderType(dest.symbolProvider().path().getClassName());
        generations.add(new SymbolProvider(dest.symbolProvider().path().removeEnd(), provider));

        // function symbols
        ArrayList<FunctionPtrType> functionPtrTypes = new ArrayList<>();
        for (Function function : functions) {
            List<FunctionPtrType.Arg> args = function.paras().stream()
                    .map(para -> new FunctionPtrType.Arg(para.paraName(), Utils.conv(para.paraType(), null))).toList();
            functionPtrTypes.add(new FunctionPtrType(function.name(), args, Utils.conv(function.ret(), null)));
        }
        generations.add(new FuncSymbols(dest.funcSymbols().path(), functionPtrTypes, provider));
    }


    public Processor withExtra(List<Function> functions, HashSet<Macro> macros, ArrayList<Declare> varDeclares,
                               HashMap<String, Type> types, Utils.DestinationProvider dest, Utils.Filter filter) {
        Generations generations = new Generations();
        processType(generations, functions, macros, varDeclares, types, dest, Collections.unmodifiableMap(processedTypes));
        processedTypes.putAll(types);
        allTypes.putAll(generations.getTypeGenerations());
        mustGenerate.addAll(generations.toGenerations(filter));
        return this;
    }

    public Processor withExtra(Analyser analyser, Utils.DestinationProvider dest, Utils.Filter filter) {
        return withExtra(analyser.getFunctions(), analyser.getMacros(), analyser.getVarDeclares(), analyser.getTypes(), dest, filter);
    }

    public void generate() {
        generator.Generator generator = new generator.Generator(mustGenerate, allTypes::get);
        generator.generate();
    }
}