package analyser;

import analyser.types.Primitive;
import analyser.types.Type;
import analyser.types.TypeDef;
import libclang.LibclangEnums;
import libclang.LibclangFunctions;
import libclang.functions.CXCursorVisitor;
import libclang.shared.*;
import libclang.shared.values.VI32;
import libclang.structs.CXCursor;
import libclang.structs.CXString;
import libclang.structs.CXToken;
import libclang.structs.CXType;
import libclang.values.*;
import utils.AutoCloseableChecker;
import utils.CheckedArena;
import utils.LoggerUtils;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.*;

import static libclang.LibclangEnums.CXErrorCode.CXError_Success;
import static libclang.LibclangEnums.CXTranslationUnit_Flags.CXTranslationUnit_DetailedPreprocessingRecord;
import static libclang.LibclangEnums.CXTranslationUnit_Flags.CXTranslationUnit_None;
import static libclang.shared.values.VI32Basic.MEMORY_LAYOUT;
import static utils.CommonUtils.*;

public class Analyser implements AutoCloseableChecker.NonThrowAutoCloseable {
    private final CheckedArena mem = CheckedArena.ofConfined();

    private final TypePool typePool = new TypePool();
    private final ArrayList<Declare> varDeclares = new ArrayList<>();
    private final ArrayList<Function> functions = new ArrayList<>();
    private final HashMap<String, String> macroDefintions = new HashMap<>();

    public Analyser(String header, List<String> args) {
        analyse(header, args, CXTranslationUnit_None.value(), new CXCursorVisitor.CXCursorVisitor$CXChildVisitResult$0() {
            @Override
            public LibclangEnums.CXChildVisitResult function(CXCursor cursor, CXCursor parent, CXClientData client_data) {
                cursor = cursor.reinterpretSize();
                Utils.printLocation(mem, cursor);
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
                    CXString varName = LibclangFunctions.clang_getCursorSpelling$CXString(mem, cursor);
                    final String[] value = {""};
                    if (findRootPrimitive(type) != null)
                        LibclangFunctions.clang_visitChildren$int(cursor, ((CXCursorVisitor.CXCursorVisitor$CXChildVisitResult$0)
                                (_cursor, _, _) -> {
                                    CXEvalResult evalResult = LibclangFunctions.clang_Cursor_Evaluate$CXEvalResult(_cursor);
                                    var evalResultKind = LibclangFunctions.clang_EvalResult_getKind$CXEvalResultKind(evalResult);
                                    if (LibclangEnums.CXEvalResultKind.CXEval_Int.equals(evalResultKind)) {
                                        value[0] = LibclangFunctions.clang_EvalResult_getAsLongLong$long(evalResult) + "";
                                    } else if (LibclangEnums.CXEvalResultKind.CXEval_Float.equals(evalResultKind)) {
                                        value[0] = LibclangFunctions.clang_EvalResult_getAsDouble$double(evalResult) + "";
                                    } else {
                                        NString initStr = new NString(LibclangFunctions.clang_EvalResult_getAsStr$Pointer(evalResult));
                                        value[0] = initStr.reinterpretSize(4096).toString();
                                    }
                                    LibclangFunctions.clang_EvalResult_dispose(evalResult);
                                    return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                                }).toVPointer(mem), new CXClientData(MemorySegment.NULL));
                    varDeclares.add(new Declare(type, Utils.cXString2String(varName), value[0]));
                    LibclangFunctions.clang_disposeString(varName);
                    return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                }

                CXType cxType = LibclangFunctions.clang_getCursorType$CXType(mem, cursor);
                CXString cursorStr = LibclangFunctions.clang_getCursorSpelling$CXString(mem, cursor);
                CXString typeStr = LibclangFunctions.clang_getTypeSpelling$CXString(mem, cxType);
                LoggerUtils.warning("Unhandled Cursor " + Utils.cXString2String(cursorStr) + " Type " + Utils.cXString2String(typeStr) + " Kind: " + kind);
                return LibclangEnums.CXChildVisitResult.CXChildVisit_Recurse;
            }
        });

        analyse(header, args, CXTranslationUnit_DetailedPreprocessingRecord.value(), new CXCursorVisitor.CXCursorVisitor$CXChildVisitResult$0() {
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
                    LibclangFunctions.clang_tokenize(tu, LibclangFunctions.clang_getCursorExtent$CXSourceRange(mem, cursor), new Pointer<Pointer<CXToken>>() {
                        @Override
                        public MemorySegment pointer() {
                            return tokensPtr;
                        }
                    }, tokenNum);
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
                    String s = macroDefintions.get(left);
                    if (s != null) {
                        if (!right.equals(s)) {
                            throw new RuntimeException("Redefine macro " + left + ":" + s);
                        }
                    }
                    macroDefintions.put(left, right);
                }
                return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
            }
        });
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

    public TypePool getTypePool() {
        return typePool;
    }

    public ArrayList<Function> getFunctions() {
        return functions;
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
        Type funcRet = typePool.addOrCreateType(returnType);
        Function func = new Function(Utils.cXString2String(funcName), funcRet);

        int numArgs = LibclangFunctions.clang_Cursor_getNumArguments$int(cur);
        for (int i = 0; i < numArgs; i++) {
            CXCursor cursor = LibclangFunctions.clang_Cursor_getArgument$CXCursor(mem, cur, i);
            CXType type = LibclangFunctions.clang_getCursorType$CXType(mem, cursor);
            CXString name = LibclangFunctions.clang_getCursorSpelling$CXString(mem, cursor);
            Type t = typePool.addOrCreateType(type, cursor, null);
            func.addPara(new Para(t, Utils.cXString2String(name), OptionalLong.empty(), OptionalLong.empty()));
        }
        functions.add(func);
    }

}
