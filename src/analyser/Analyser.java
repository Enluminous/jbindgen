package analyser;

import analyser.types.Primitive;
import analyser.types.Type;
import analyser.types.TypeDef;
import libclang.LibclangFunctionSymbols;
import libclang.common.*;
import libclang.enumerates.*;
import libclang.functions.CXCursorVisitor;
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
import java.util.*;

import static libclang.enumerates.CXEvalResultKind.CXEval_Float;
import static libclang.enumerates.CXEvalResultKind.CXEval_Int;
import static utils.CommonUtils.Assert;

public class Analyser implements AutoCloseableChecker.NonThrowAutoCloseable {
    private final CheckedArena mem = CheckedArena.ofConfined();

    private final TypePool typePool = new TypePool();
    private final ArrayList<Declare> varDeclares = new ArrayList<>();
    private final HashMap<String, Function> functions = new HashMap<>();

    private final HashSet<Macro> macros = new HashSet<>();

    public Analyser(String header, List<String> args, boolean analyseMacro) {
        analyse(header, args, CXTranslationUnit_Flags.CXTranslationUnit_None.operator().value(), new CXCursorVisitor(mem, (CXCursorVisitor.Function) (cursor, parent, client_data) -> {
            Utils.printLocation(cursor);
            var kind = LibclangFunctionSymbols.clang_getCursorKind(cursor);
            if (CXCursorKind.CXCursor_StructDecl.equals(kind)) {
                typePool.addOrCreateStruct(cursor, null);
                return CXChildVisitResult.CXChildVisit_Continue;
            }
            if (CXCursorKind.CXCursor_TypedefDecl.equals(kind)) {
                typePool.addOrCreateTypeDef(cursor);
                return CXChildVisitResult.CXChildVisit_Continue;
            }
            if (CXCursorKind.CXCursor_FunctionDecl.equals(kind)) {
                DeclaredFunctionBuilder(cursor);
                return CXChildVisitResult.CXChildVisit_Continue;
            }
            if (CXCursorKind.CXCursor_EnumDecl.equals(kind)) {
                typePool.addOrCreateType(cursor, null);
                return CXChildVisitResult.CXChildVisit_Continue;
            }
            if (CXCursorKind.CXCursor_UnionDecl.equals(kind)) {
                typePool.addOrCreateUnion(cursor, null);
                return CXChildVisitResult.CXChildVisit_Continue;
            }
            if (CXCursorKind.CXCursor_VarDecl.equals(kind)) {
                CXType cxType = LibclangFunctionSymbols.clang_getCursorType(mem, cursor);
                Type type = typePool.addOrCreateType(cxType);
                if (findRootPrimitive(type) != null && LibclangFunctionSymbols.clang_isConstQualifiedType(cxType).operator().value() != 0) {
                    CXString cxVarName = LibclangFunctionSymbols.clang_getCursorSpelling(mem, cursor);
                    String varName = Utils.cXString2String(cxVarName);
                    LibclangFunctionSymbols.clang_disposeString(cxVarName);

                    CXCursor initializer = LibclangFunctionSymbols.clang_Cursor_getVarDeclInitializer(mem, cursor);
                    CXEvalResult evalResult = LibclangFunctionSymbols.clang_Cursor_Evaluate(initializer);
                    var evalResultKind = LibclangFunctionSymbols.clang_EvalResult_getKind(evalResult);

                    String value = null;
                    if (CXEval_Int.equals(evalResultKind)) {
                        value = LibclangFunctionSymbols.clang_EvalResult_getAsLongLong(evalResult) + "L";
                    } else if (CXEval_Float.equals(evalResultKind)) {
                        value = LibclangFunctionSymbols.clang_EvalResult_getAsDouble(evalResult) + "";
                    } else {
                        System.out.println("ignore var declaration type " + evalResultKind);
                    }
                    LibclangFunctionSymbols.clang_EvalResult_dispose(evalResult);
                    if (value != null)
                        varDeclares.add(new Declare(type, varName, value));
                }
                return CXChildVisitResult.CXChildVisit_Continue;
            }

            CXType cxType = LibclangFunctionSymbols.clang_getCursorType(mem, cursor);
            CXString cursorStr = LibclangFunctionSymbols.clang_getCursorSpelling(mem, cursor);
            CXString typeStr = LibclangFunctionSymbols.clang_getTypeSpelling(mem, cxType);
            LoggerUtils.warning("Unhandled Cursor " + Utils.cXString2String(cursorStr) + " Type " + Utils.cXString2String(typeStr) + " Kind: " + kind);
            return CXChildVisitResult.CXChildVisit_Recurse;
        }));
        if (!analyseMacro)
            return;

        HashMap<String, String> macroDefinitions = new HashMap<>();
        analyse(header, args, CXTranslationUnit_Flags.CXTranslationUnit_DetailedPreprocessingRecord.operator().value(), new CXCursorVisitor(mem, (CXCursorVisitor.Function) (CXCursor cursor, CXCursor parent, CXClientData client_data) -> {
            if (CXCursorKind.CXCursor_MacroDefinition.equals(cursor.kind())) {
                CXTranslationUnit tu = LibclangFunctionSymbols.clang_Cursor_getTranslationUnit(cursor);
                CXString leftCxString = LibclangFunctionSymbols.clang_getCursorSpelling(mem, cursor);
                String left = Utils.cXString2String(leftCxString);
                String right = "";
                LibclangFunctionSymbols.clang_disposeString(leftCxString);
                Array<Array<CXToken>> tokensPtr = new Array<>(mem, Array.makeOperations(CXToken.OPERATIONS), 1);
                Array<I32> tokenNum = I32.list(mem, 1);
                LibclangFunctionSymbols.clang_tokenize(tu, LibclangFunctionSymbols.clang_getCursorExtent(mem, cursor), tokensPtr, tokenNum);
                Integer num = tokenNum.getFirst().operator().value();
                Array<CXToken> tokenList = tokensPtr.operator().pointee().operator().reinterpret(num);
                CXString firstTokenCxString = LibclangFunctionSymbols.clang_getTokenSpelling(mem, tu, tokenList.getFirst());
                String firstTokenString = Utils.cXString2String(firstTokenCxString);
                LibclangFunctionSymbols.clang_disposeString(firstTokenCxString);
                if (firstTokenString.equals(left)) {
                    for (int i = 1; i < num; i++) {
                        CXString tokenCxString = LibclangFunctionSymbols.clang_getTokenSpelling(mem, tu, tokenList.get(i));
                        String tokenString = Utils.cXString2String(tokenCxString);
                        LibclangFunctionSymbols.clang_disposeString(tokenCxString);
                        right += tokenString;
                    }
                }
                LibclangFunctionSymbols.clang_disposeTokens(tu, tokenList, I32I.of(num));
                String s = macroDefinitions.get(left);
                if (s != null) {
                    if (!right.equals(s)) {
                        System.out.println("Overwrite redefined macro: " + left + " new: " + right + " old: " + s);
                    }
                }
                macroDefinitions.put(left, right);
            }
            return CXChildVisitResult.CXChildVisit_Continue;
        }));

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
        macros.add(new Macro(PrimitiveTypes.JType.J_String, kv.getKey(), v, kv.getValue().replace("\\\n", " ")));
    }

    private void addMacroLong(Map.Entry<String, String> kv, long v) {
        macros.add(new Macro(PrimitiveTypes.CType.C_I64, kv.getKey(), v + "L", kv.getValue()));
    }

    private void addMacroInt(Map.Entry<String, String> kv, int v) {
        macros.add(new Macro(PrimitiveTypes.CType.C_I32, kv.getKey(), v + "", kv.getValue()));
    }

    private void addMacroByte(Map.Entry<String, String> kv, int v) {
        String value = v + "";
        if (v > 127)
            value = "(byte)" + v;
        if (v > 255) {
            throw new RuntimeException();
        }
        macros.add(new Macro(PrimitiveTypes.CType.C_I8, kv.getKey(), value, kv.getValue()));
    }

    private void addMacroFloat(Map.Entry<String, String> kv, float v) {
        macros.add(new Macro(PrimitiveTypes.CType.C_FP32, kv.getKey(), v + "F", kv.getValue()));
    }

    private void addMacroDouble(Map.Entry<String, String> kv, double v) {
        macros.add(new Macro(PrimitiveTypes.CType.C_FP64, kv.getKey(), v + "", kv.getValue()));
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
                        """.formatted(header, kv.getKey());
                generator.Utils.write(temp.toPath(), content);
                final boolean[] searched = {false};
                analyse(temp.getAbsolutePath(), args,
                        CXTranslationUnit_Flags.CXTranslationUnit_Incomplete.operator().value() |
                                CXTranslationUnit_Flags.CXTranslationUnit_IncludeAttributedTypes.operator().value(),
                        (new CXCursorVisitor(mem, new CXCursorVisitor.Function() {
                            @Override
                            public CXChildVisitResult CXCursorVisitor(CXCursor cursor, CXCursor parent, CXClientData client_data) {
                                CXType autoType = LibclangFunctionSymbols.clang_getCursorType(mem, cursor);
                                if (!CXTypeKind.CXType_Auto.equals(autoType.kind())) {
                                    return CXChildVisitResult.CXChildVisit_Continue;
                                }
                                searched[0] = true;
                                CXCursor decl = LibclangFunctionSymbols.clang_Cursor_getVarDeclInitializer(mem, cursor);
                                CXType declType = LibclangFunctionSymbols.clang_getCursorType(mem, decl);
                                long size = LibclangFunctionSymbols.clang_Type_getSizeOf(declType).operator().value();
                                CXTypeKind declKind = declType.kind();
                                CXEvalResult declEvaluate = LibclangFunctionSymbols.clang_Cursor_Evaluate(decl);
                                CXEvalResultKind evalResultKind = LibclangFunctionSymbols.clang_EvalResult_getKind(declEvaluate);
                                if (CXEvalResultKind.CXEval_Float.equals(evalResultKind)) {
                                    if (declKind.equals(CXTypeKind.CXType_Float) ||
                                            declKind.equals(CXTypeKind.CXType_Float16)) {
                                        double v = LibclangFunctionSymbols.clang_EvalResult_getAsDouble(declEvaluate).operator().value();
                                        addMacroFloat(kv, (float) v);
                                    } else if (declKind.equals(CXTypeKind.CXType_Double)) {
                                        double v = LibclangFunctionSymbols.clang_EvalResult_getAsDouble(declEvaluate).operator().value();
                                        addMacroDouble(kv, v);
                                    } else {
                                        // fallback as string
                                        addMacroString(kv);
                                    }
                                } else if (CXEvalResultKind.CXEval_Int.equals(evalResultKind)) {
                                    if (size == 1) {
                                        int v = LibclangFunctionSymbols.clang_EvalResult_getAsInt(declEvaluate).operator().value();
                                        addMacroByte(kv, v);
                                    } else if (size == 4) {
                                        int v = LibclangFunctionSymbols.clang_EvalResult_getAsInt(declEvaluate).operator().value();
                                        addMacroInt(kv, v);
                                    } else if (size == 8) {
                                        long v = LibclangFunctionSymbols.clang_EvalResult_getAsLongLong(declEvaluate).operator().value();
                                        addMacroLong(kv, v);
                                    } else {
                                        throw new RuntimeException();
                                    }
                                } else if (CXEvalResultKind.CXEval_ObjCStrLiteral.equals(evalResultKind) ||
                                        CXEvalResultKind.CXEval_StrLiteral.equals(evalResultKind) ||
                                        CXEvalResultKind.CXEval_CFStr.equals(evalResultKind)) {
                                    addMacroString(kv);
                                } else {
                                    System.out.println("Ignore unsupported macro: " + kv);
                                }
                                return CXChildVisitResult.CXChildVisit_Continue;
                            }
                        })));
                if (!searched[0]) {
                    throw new RuntimeException();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void analyse(String header, List<String> args, int options, CXCursorVisitor visitor) {
        Array<CXTranslationUnit> unit4declaration = CXTranslationUnit.list(mem, 1);
        CXIndex index4declaration = LibclangFunctionSymbols.clang_createIndex(I32I.of(0), I32I.of(0));
        CXErrorCode err = LibclangFunctionSymbols.clang_parseTranslationUnit2(
                index4declaration,
                new Str(mem, header), Str.list(mem, args), I32I.of(args.size()),
                PtrI.of(MemorySegment.NULL), I32I.of(0),
                I32I.of(options), unit4declaration);
        if (!err.equals(CXErrorCode.CXError_Success)) {
            Assert(false, "Unable to parse translation unit (" + err + ").");
        }
        CXTargetInfo cxTargetInfo = LibclangFunctionSymbols.clang_getTranslationUnitTargetInfo(unit4declaration.getFirst());
        int width = LibclangFunctionSymbols.clang_TargetInfo_getPointerWidth(cxTargetInfo).operator().value();
        if (width != 64) {
            boolean ignore = System.getenv("IGNORE_POINTER_WIDTH").equalsIgnoreCase("true");
            Assert(ignore, "PointerWidth != 64 is not supported! set env var IGNORE_POINTER_WIDTH to true to ignore this check");
        }
        CXCursor cxCursor = LibclangFunctionSymbols.clang_getTranslationUnitCursor(mem, unit4declaration.getFirst());
        LibclangFunctionSymbols.clang_visitChildren(cxCursor, visitor, new CXClientData(MemorySegment.NULL));
        LibclangFunctionSymbols.clang_disposeIndex(index4declaration);
        LibclangFunctionSymbols.clang_disposeTranslationUnit(unit4declaration.getFirst());
    }

    public ArrayList<Declare> getVarDeclares() {
        return varDeclares;
    }

    public ArrayList<Function> getFunctions() {
        return new ArrayList<>(functions.values());
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
        CXType cxType = LibclangFunctionSymbols.clang_getCursorType(mem, cur);
        var cxTypeName = LibclangFunctionSymbols.clang_getTypeSpelling(mem, cxType);
        var typeName = Utils.cXString2String(cxTypeName);
        LibclangFunctionSymbols.clang_disposeString(cxTypeName);

        CXString cxFuncName = LibclangFunctionSymbols.clang_getCursorSpelling(mem, cur);
        String funcName = Utils.cXString2String(cxFuncName);
        LibclangFunctionSymbols.clang_disposeString(cxFuncName);
        if (LibclangFunctionSymbols.clang_Cursor_isFunctionInlined(cur).operator().value() != 0) {
            System.out.println("Ignore inlined function " + funcName + " " + typeName);
            return;
        }
        CXType returnType = LibclangFunctionSymbols.clang_getCursorResultType(mem, cur);
        Type funcRet = typePool.addOrCreateType(returnType, cur, null);
        Function func = new Function(funcName, funcRet, typeName);

        int numArgs = LibclangFunctionSymbols.clang_Cursor_getNumArguments(cur).operator().value();
        for (int i = 0; i < numArgs; i++) {
            CXCursor cursor = LibclangFunctionSymbols.clang_Cursor_getArgument(mem, cur, I32I.of(i));
            CXType type = LibclangFunctionSymbols.clang_getCursorType(mem, cursor);
            CXString name = LibclangFunctionSymbols.clang_getCursorSpelling(mem, cursor);
            Type t = typePool.addOrCreateType(type, cursor, null);
            String paraName = Utils.cXString2String(name);
            if (paraName.isEmpty())
                paraName = "arg" + i;
            func.addPara(new Para(t, paraName, OptionalLong.empty(), OptionalLong.empty(), OptionalLong.empty()));
        }
        Function function = functions.get(funcName);
        if (function != null) {
            if (function.signature().equals(typeName))
                return;
            else
                Assert(false);
        }
        functions.put(funcName, func);
    }

}
