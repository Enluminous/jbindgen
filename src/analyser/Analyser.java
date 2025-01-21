package analyser;

import analyser.types.Primitive;
import analyser.types.Type;
import analyser.types.TypeDef;
import libclang.LibclangEnums;
import libclang.LibclangFunctions;
import libclang.functions.CXCursorVisitor;
import libclang.shared.NString;
import libclang.shared.VPointerList;
import libclang.structs.CXCursor;
import libclang.structs.CXString;
import libclang.structs.CXType;
import libclang.values.*;
import utils.AutoCloseableChecker;
import utils.CheckedArena;
import utils.LoggerUtils;

import java.lang.foreign.MemorySegment;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.OptionalLong;

import static libclang.LibclangEnums.CXErrorCode.CXError_Success;
import static libclang.LibclangEnums.CXTranslationUnit_Flags.CXTranslationUnit_None;
import static utils.CommonUtils.*;

public class Analyser implements AutoCloseableChecker.NonThrowAutoCloseable {
    private final CheckedArena mem = CheckedArena.ofConfined();
    private final VPointerList<CXTranslationUnit> unit4declaration;

    private final TypePool typePool = new TypePool();
    private final ArrayList<Declare> varDeclares = new ArrayList<>();
    private final ArrayList<Function> functions = new ArrayList<>();

    public Analyser(String header, List<String> args) {
        unit4declaration = CXTranslationUnit.list(mem, 1);

        CXIndex index4declaration = LibclangFunctions.clang_createIndex$CXIndex(0, 0);
        LibclangEnums.CXErrorCode err = LibclangFunctions.clang_parseTranslationUnit2$CXErrorCode(
                index4declaration,
                new NString(mem, header), NString.list(mem, args)::pointer, args.size(),
                nullptr(), 0,
                CXTranslationUnit_None.value(), unit4declaration);
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
        LibclangFunctions.clang_visitChildren$int(cxCursor, ((CXCursorVisitor.CXCursorVisitor$CXChildVisitResult$0) (cursor, parent, _) -> {
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
        }).toVPointer(mem), new CXClientData(MemorySegment.NULL));
        LibclangFunctions.clang_disposeIndex(index4declaration);
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
        LibclangFunctions.clang_disposeTranslationUnit(unit4declaration.getFirst());
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
            Type t = typePool.addOrCreateType(type);
            func.addPara(new Para(t, Utils.cXString2String(name), OptionalLong.empty(), OptionalInt.empty()));
        }
        functions.add(func);
    }

}
