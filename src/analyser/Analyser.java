package analyser;

import analyser.types.Primitive;
import analyser.types.Type;
import analyser.types.TypeDef;
import libclang.LibclangEnums;
import libclang.LibclangFunctions;
import libclang.functions.CXCursorVisitor;
import libclang.shared.NList;
import libclang.shared.NString;
import libclang.shared.VI32List;
import libclang.shared.VPointerList;
import libclang.shared.values.VI32;
import libclang.structs.CXCursor;
import libclang.structs.CXString;
import libclang.structs.CXToken;
import libclang.structs.CXType;
import libclang.values.*;
import utils.AutoCloseableChecker;
import utils.CheckedArena;
import utils.LoggerUtils;

import java.io.File;
import java.io.IOException;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.*;

import static libclang.LibclangEnums.CXErrorCode.CXError_Success;
import static utils.CommonUtils.*;

public class Analyser implements AutoCloseableChecker.NonThrowAutoCloseable {
    private final CheckedArena mem = CheckedArena.ofConfined();

    private final TypePool typePool = new TypePool();
    private final ArrayList<Declare> varDeclares = new ArrayList<>();
    private final ArrayList<Function> functions = new ArrayList<>();

    private final HashSet<Macro> macros = new HashSet<>();

    public Analyser(String header, List<String> args, boolean analyseMacro) {
        analyse(header, args, LibclangEnums.CXTranslationUnit_Flags.CXTranslationUnit_None.value(), new CXCursorVisitor.CXCursorVisitor$CXChildVisitResult$0() {
            @Override
            public LibclangEnums.CXChildVisitResult function(CXCursor cursor, CXCursor parent, CXClientData client_data) {
                cursor = cursor.reinterpretSize();
                Utils.printLocation(cursor);
                var kind = LibclangFunctions.clang_getCursorKind$CXCursorKind(cursor);
                if (LibclangEnums.CXCursorKind.CXCursor_StructDecl.equals(kind)) {
//                typePool.addOrCreateStruct(cursor, null);
                    return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                }
                if (LibclangEnums.CXCursorKind.CXCursor_TypedefDecl.equals(kind)) {
//                typePool.addOrCreateTypeDef(cursor);
                    return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                }
                if (LibclangEnums.CXCursorKind.CXCursor_FunctionDecl.equals(kind)) {
                    DeclaredFunctionBuilder(cursor);
                    return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                }
                if (LibclangEnums.CXCursorKind.CXCursor_EnumDecl.equals(kind)) {
                    typePool.addOrCreateType(cursor, null);
                    return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                }
                if (LibclangEnums.CXCursorKind.CXCursor_UnionDecl.equals(kind)) {
                    typePool.addOrCreateUnion(cursor, null);
                    return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                }
                if (LibclangEnums.CXCursorKind.CXCursor_VarDecl.equals(kind)) {
                    CXType cxType = LibclangFunctions.clang_getCursorType$CXType(mem, cursor);
                    Type type = typePool.addOrCreateType(cxType);
                    if (findRootPrimitive(type) != null && LibclangFunctions.clang_isConstQualifiedType$int(cxType) != 0) {
                        CXString cxVarName = LibclangFunctions.clang_getCursorSpelling$CXString(mem, cursor);
                        String varName = Utils.cXString2String(cxVarName);
                        LibclangFunctions.clang_disposeString(cxVarName);

                        CXCursor initializer = LibclangFunctions.clang_Cursor_getVarDeclInitializer$CXCursor(mem, cursor);
                        CXEvalResult evalResult = LibclangFunctions.clang_Cursor_Evaluate$CXEvalResult(initializer);
                        var evalResultKind = LibclangFunctions.clang_EvalResult_getKind$CXEvalResultKind(evalResult);

                        String value = null;
                        if (LibclangEnums.CXEvalResultKind.CXEval_Int.equals(evalResultKind)) {
                            value = LibclangFunctions.clang_EvalResult_getAsLongLong$long(evalResult) + "L";
                        } else if (LibclangEnums.CXEvalResultKind.CXEval_Float.equals(evalResultKind)) {
                            value = LibclangFunctions.clang_EvalResult_getAsDouble$double(evalResult) + "";
                        } else {
                            System.out.println("ignore var declaration type " + evalResultKind);
                        }
                        LibclangFunctions.clang_EvalResult_dispose(evalResult);
                        if (value != null)
                            varDeclares.add(new Declare(type, varName, value));
                    }
                    return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                }

                CXType cxType = LibclangFunctions.clang_getCursorType$CXType(mem, cursor);
                CXString cursorStr = LibclangFunctions.clang_getCursorSpelling$CXString(mem, cursor);
                CXString typeStr = LibclangFunctions.clang_getTypeSpelling$CXString(mem, cxType);
                LoggerUtils.warning("Unhandled Cursor " + Utils.cXString2String(cursorStr) + " Type " + Utils.cXString2String(typeStr) + " Kind: " + kind);
                return LibclangEnums.CXChildVisitResult.CXChildVisit_Recurse;
            }
        });
        if (Integer.valueOf(1).equals(1))
            return;
        if (!analyseMacro)
            return;

        HashMap<String, String> macroDefinitions = new HashMap<>();
        analyse(header, args, LibclangEnums.CXTranslationUnit_Flags.CXTranslationUnit_DetailedPreprocessingRecord.value(), new CXCursorVisitor.CXCursorVisitor$CXChildVisitResult$0() {
            @Override
            public LibclangEnums.CXChildVisitResult function(CXCursor cursor, CXCursor parent, CXClientData client_data) {
                if (LibclangEnums.CXCursorKind.CXCursor_MacroDefinition.equals(cursor.kind())) {
                    CXTranslationUnit tu = LibclangFunctions.clang_Cursor_getTranslationUnit$CXTranslationUnit(cursor);
                    CXString leftCxString = LibclangFunctions.clang_getCursorSpelling$CXString(mem, cursor);
                    String left = Utils.cXString2String(leftCxString);
                    String right = "";
                    LibclangFunctions.clang_disposeString(leftCxString);
                    MemorySegment tokensPtr = mem.allocate(ValueLayout.ADDRESS);
                    VI32List<VI32<Integer>> tokenNum = VI32.list(mem, 1);
                    LibclangFunctions.clang_tokenize(tu, LibclangFunctions.clang_getCursorExtent$CXSourceRange(mem, cursor), () -> tokensPtr, tokenNum);
                    Integer num = tokenNum.getFirst().value();
                    NList<CXToken> tokenList = CXToken.list(() -> tokensPtr.get(ValueLayout.ADDRESS, 0), num);
                    CXString firstTokenCxString = LibclangFunctions.clang_getTokenSpelling$CXString(mem, tu, tokenList.getFirst());
                    String firstTokenString = Utils.cXString2String(firstTokenCxString);
                    LibclangFunctions.clang_disposeString(firstTokenCxString);
                    if (firstTokenString.equals(left)) {
                        for (int i = 1; i < num; i++) {
                            CXString tokenCxString = LibclangFunctions.clang_getTokenSpelling$CXString(mem, tu, tokenList.get(i));
                            String tokenString = Utils.cXString2String(tokenCxString);
                            LibclangFunctions.clang_disposeString(tokenCxString);
                            right += tokenString;
                        }
                    }
                    LibclangFunctions.clang_disposeTokens(tu, tokenList, num);
                    String s = macroDefinitions.get(left);
                    if (s != null) {
                        if (!right.equals(s)) {
                            throw new RuntimeException("Redefine macro " + left + ":" + s);
                        }
                    }
                    macroDefinitions.put(left, right);
                }
                return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
            }
        });

        processMacroDefinitions(macroDefinitions, header, args);
    }

    private void addMacroString(Map.Entry<String, String> kv) {
        String v = kv.getValue().trim();
        java.util.function.Function<String, String> processStr = input -> {
            input = input.replace("\\\n", " ");
            if (input.startsWith("\"") && input.endsWith("\"")) {
                input = input.replaceAll("\"\\s*\"", "");
                return input;
            } else {
                String escapedString = input.replace("\"", "\\\"");
                return "\"" + escapedString + "\"";
            }
        };
        v = processStr.apply(v);
        macros.add(new Macro(kv.getKey(), kv.getValue().replace("\\\n", " "), "String", v));
    }

    private void addMacroLong(Map.Entry<String, String> kv, long v) {
        macros.add(new Macro(kv.getKey(), kv.getValue(), "long", v + "L"));
    }

    private void addMacroInt(Map.Entry<String, String> kv, int v) {
        macros.add(new Macro(kv.getKey(), kv.getValue(), "int", v + ""));
    }

    private void addMacroByte(Map.Entry<String, String> kv, int v) {
        String value = v + "";
        if (v > 127)
            value = "(byte)" + v;
        if (v > 255) {
            throw new RuntimeException();
        }
        macros.add(new Macro(kv.getKey(), kv.getValue(), "byte", value));
    }

    private void addMacroFloat(Map.Entry<String, String> kv, float v) {
        macros.add(new Macro(kv.getKey(), kv.getValue(), "float", v + "F"));
    }

    private void addMacroDouble(Map.Entry<String, String> kv, double v) {
        macros.add(new Macro(kv.getKey(), kv.getValue(), "double", v + ""));
    }

    private void processMacroDefinitions(HashMap<String, String> macroDefinitions, String header, List<String> args) {
        header = new File(header).getAbsolutePath();
        try {
            File temp = File.createTempFile("macro-", ".cpp");
            for (Map.Entry<String, String> kv : macroDefinitions.entrySet()) {
                if (kv.getValue().isEmpty())
                    continue;
                String content = """
                        #include "%s"
                        auto x=%s;
                        """.formatted(header, kv.getValue());
                generator.Utils.write(temp.toPath(), content);
                final boolean[] searched = {false};
                analyse(temp.getAbsolutePath(), args,
                        LibclangEnums.CXTranslationUnit_Flags.CXTranslationUnit_Incomplete.value() |
                                LibclangEnums.CXTranslationUnit_Flags.CXTranslationUnit_IncludeAttributedTypes.value(),
                        (CXCursorVisitor.CXCursorVisitor$CXChildVisitResult$0) (cursor, _, _) -> {
                            CXType autoType = LibclangFunctions.clang_getCursorType$CXType(mem, cursor);
                            if (!LibclangEnums.CXTypeKind.CXType_Auto.equals(autoType.kind())) {
                                return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                            }
                            searched[0] = true;
                            CXCursor decl = LibclangFunctions.clang_Cursor_getVarDeclInitializer$CXCursor(mem, cursor);
                            CXType declType = LibclangFunctions.clang_getCursorType$CXType(mem, decl);
                            long size = LibclangFunctions.clang_Type_getSizeOf$long(declType);
                            LibclangEnums.CXTypeKind declKind = declType.kind();
                            CXEvalResult declEvaluate = LibclangFunctions.clang_Cursor_Evaluate$CXEvalResult(decl);
                            LibclangEnums.CXEvalResultKind evalResultKind = LibclangFunctions.clang_EvalResult_getKind$CXEvalResultKind(declEvaluate);
                            if (LibclangEnums.CXEvalResultKind.CXEval_Float.equals(evalResultKind)) {
                                if (declKind.equals(LibclangEnums.CXTypeKind.CXType_Float) ||
                                        declKind.equals(LibclangEnums.CXTypeKind.CXType_Float16)) {
                                    double v = LibclangFunctions.clang_EvalResult_getAsDouble$double(declEvaluate);
                                    addMacroFloat(kv, (float) v);
                                } else if (declKind.equals(LibclangEnums.CXTypeKind.CXType_Double)) {
                                    double v = LibclangFunctions.clang_EvalResult_getAsDouble$double(declEvaluate);
                                    addMacroDouble(kv, v);
                                } else {
                                    // fallback as string
                                    addMacroString(kv);
                                }
                            } else if (LibclangEnums.CXEvalResultKind.CXEval_Int.equals(evalResultKind)) {
                                if (size == 1) {
                                    int v = LibclangFunctions.clang_EvalResult_getAsInt$int(declEvaluate);
                                    addMacroByte(kv, v);
                                } else if (size == 4) {
                                    int v = LibclangFunctions.clang_EvalResult_getAsInt$int(declEvaluate);
                                    addMacroInt(kv, v);
                                } else if (size == 8) {
                                    long v = LibclangFunctions.clang_EvalResult_getAsLongLong$long(declEvaluate);
                                    addMacroLong(kv, v);
                                } else {
                                    throw new RuntimeException();
                                }
                            } else if (LibclangEnums.CXEvalResultKind.CXEval_ObjCStrLiteral.equals(evalResultKind) ||
                                    LibclangEnums.CXEvalResultKind.CXEval_StrLiteral.equals(evalResultKind) ||
                                    LibclangEnums.CXEvalResultKind.CXEval_CFStr.equals(evalResultKind)) {
                                addMacroString(kv);
                            } else {
                                System.out.println("Ignore macro: " + kv);
                            }
                            return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                        }
                );
                if (!searched[0]) {
                    throw new RuntimeException();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void analyse(String header, List<String> args, int options, CXCursorVisitor visitor) {
        VPointerList<CXTranslationUnit> unit4declaration = CXTranslationUnit.list(mem, 1);
        CXIndex index4declaration = LibclangFunctions.clang_createIndex$CXIndex(0, 0);
        LibclangEnums.CXErrorCode err = LibclangFunctions.clang_parseTranslationUnit2$CXErrorCode(
                index4declaration,
                new NString(mem, header), NString.list(mem, args)::pointer, args.size(),
                nullptr(), 0,
                options, unit4declaration);
        if (!err.equals(CXError_Success) || isNull(unit4declaration)) {
            Assert(false, "Unable to parse translation unit (" + err + ").");
        }
        CXTargetInfo cxTargetInfo = LibclangFunctions.clang_getTranslationUnitTargetInfo$CXTargetInfo(unit4declaration.getFirst());
        int width = LibclangFunctions.clang_TargetInfo_getPointerWidth$int(cxTargetInfo);
        if (width != 64) {
            boolean ignore = System.getenv("IGNORE_POINTER_WIDTH").equalsIgnoreCase("true");
            Assert(ignore, "PointerWidth != 64 is not supported! set env var IGNORE_POINTER_WIDTH to true to ignore this check");
        }
        CXCursor cxCursor = LibclangFunctions.clang_getTranslationUnitCursor$CXCursor(mem, unit4declaration.getFirst());
        LibclangFunctions.clang_visitChildren$int(cxCursor, visitor.toVPointer(mem), new CXClientData(MemorySegment.NULL));
        LibclangFunctions.clang_disposeIndex(index4declaration);
        LibclangFunctions.clang_disposeTranslationUnit(unit4declaration.getFirst());
    }

    public ArrayList<Declare> getVarDeclares() {
        return varDeclares;
    }

    public ArrayList<Function> getFunctions() {
        return functions;
    }

    public HashSet<Macro> getMacros() {
        return macros;
    }

    public HashMap<String, Type> getTypes() {
        return typePool.getTypes();
    }

    private static Primitive findRootPrimitive(Type type) {
        if (type instanceof TypeDef d) {
            return findRootPrimitive(d.getTarget());
        }
        if (type instanceof Primitive p)
            return p;
        return null;
    }

    @Override
    public void close() {
        mem.close();
        typePool.close();
    }

    private void DeclaredFunctionBuilder(CXCursor cur) {
        CXString funcName = LibclangFunctions.clang_getCursorSpelling$CXString(mem, cur);
        CXType returnType = LibclangFunctions.clang_getCursorResultType$CXType(mem, cur);
        Type funcRet = typePool.addOrCreateType(returnType, cur, null);
        Function func = new Function(Utils.cXString2String(funcName), funcRet);

        int numArgs = LibclangFunctions.clang_Cursor_getNumArguments$int(cur);
        for (int i = 0; i < numArgs; i++) {
            CXCursor cursor = LibclangFunctions.clang_Cursor_getArgument$CXCursor(mem, cur, i);
            CXType type = LibclangFunctions.clang_getCursorType$CXType(mem, cursor);
            CXString name = LibclangFunctions.clang_getCursorSpelling$CXString(mem, cursor);
            Type t = typePool.addOrCreateType(type, cursor, null);
            String paraName = Utils.cXString2String(name);
            if (paraName.isEmpty())
                paraName = "arg" + i;
            func.addPara(new Para(t, paraName, OptionalLong.empty(), OptionalLong.empty()));
        }
        functions.add(func);
    }

}
