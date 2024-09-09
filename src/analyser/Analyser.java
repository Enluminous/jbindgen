package analyser;

import analyser.types.Type;
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

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.ArrayList;
import java.util.List;

import static libclang.LibclangEnums.CXErrorCode.CXError_Success;
import static libclang.LibclangEnums.CXTranslationUnit_Flags.CXTranslationUnit_None;
import static utils.CommonUtils.*;

public class Analyser implements AutoCloseableChecker.NonThrowAutoCloseable {
    private final CheckedArena mem = CheckedArena.ofConfined();
    private final VPointerList<CXTranslationUnit> unit4declaration;

    private final TypePool typePool = new TypePool();
    private final ArrayList<Struct> structs = new ArrayList<>();
    private final ArrayList<Function> functions = new ArrayList<>();

    public Analyser(String header, List<String> args) {
        unit4declaration = CXTranslationUnit.list(mem, 1);

        CXIndex index4declaration = LibclangFunctions.clang_createIndex$CXIndex(0, 0);
        try (Arena arena = Arena.ofConfined()) {
            LibclangEnums.CXErrorCode err = LibclangFunctions.clang_parseTranslationUnit2$CXErrorCode(
                    index4declaration,
                    new NString(arena, header), NString.list(arena, args)::pointer, args.size(),
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
            CXCursor cxCursor = LibclangFunctions.clang_getTranslationUnitCursor$CXCursor(arena, unit4declaration.getFirst());
            LibclangFunctions.clang_visitChildren$int(cxCursor, ((CXCursorVisitor.CXCursorVisitor$CXChildVisitResult$0) (cursor, parent, _) -> {
                cursor = cursor.reinterpretSize();
                Utils.printLocation(mem, cursor);
                var kind = LibclangFunctions.clang_getCursorKind$CXCursorKind(cursor);
                if (LibclangEnums.CXCursorKind.CXCursor_StructDecl.equals(kind)) {
                    DeclaredStructBuilder(cursor);
                    return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                }
                if (LibclangEnums.CXCursorKind.CXCursor_TypedefDecl.equals(kind)) {
                    TypeDeclaredBuilder(cursor);
                    return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                }
                if (LibclangEnums.CXCursorKind.CXCursor_FunctionDecl.equals(kind)) {
                    DeclaredFunctionBuilder(cursor);
                    return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                }
                if (LibclangEnums.CXCursorKind.CXCursor_EnumDecl.equals(kind)) {
                    typePool.addOrCreateType(cursor);
                    return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                }
                if (LibclangEnums.CXCursorKind.CXCursor_UnionDecl.equals(kind)) {
                    typePool.addOrCreateUnion(cursor);
                    return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                }


                CXType cxType = LibclangFunctions.clang_getCursorType$CXType(arena, cursor);
                CXString cursorStr = LibclangFunctions.clang_getCursorSpelling$CXString(arena, cursor);
                CXString typeStr = LibclangFunctions.clang_getTypeSpelling$CXString(arena, cxType);
                LoggerUtils.debug("Cursor " + Utils.cXString2String(cursorStr) + " Type " + Utils.cXString2String(typeStr) + " Kind: " + kind);
                return LibclangEnums.CXChildVisitResult.CXChildVisit_Recurse;
            }).toVPointer(arena), new CXClientData(MemorySegment.NULL));
        }
        LibclangFunctions.clang_disposeIndex(index4declaration);
    }

    public ArrayList<Struct> getStructs() {
        return structs;
    }

    public TypePool getTypePool() {
        return typePool;
    }

    public ArrayList<Function> getFunctions() {
        return functions;
    }

    @Override
    public void close() {
        LibclangFunctions.clang_disposeTranslationUnit(unit4declaration.getFirst());
        mem.close();
        typePool.close();
    }

    public void DeclaredStructBuilder(CXCursor cur) {
        CXString structName_ = LibclangFunctions.clang_getCursorSpelling$CXString(mem, cur);
        String structName = Utils.cXString2String(structName_);
        var currentStruct = typePool.addOrCreateStruct(cur);
        Struct struct = new Struct(currentStruct, structName);
        structs.add(struct);

        LoggerUtils.info("Declared " + struct);

        LibclangFunctions.clang_disposeString(structName_);
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
            func.addPara(new Para(t, Utils.cXString2String(name)));
        }
        functions.add(func);
    }

    private void TypeDeclaredBuilder(CXCursor cur) {
        typePool.addOrCreateTypeDef(cur);
    }
}
