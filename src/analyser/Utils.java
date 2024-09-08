package analyser;

import libclang.LibclangFunctions;
import libclang.shared.NString;
import libclang.shared.Pointer;
import libclang.shared.VI32List;
import libclang.shared.VPointerList;
import libclang.shared.values.VI32;
import libclang.shared.values.VI8;
import libclang.structs.CXCursor;
import libclang.structs.CXSourceLocation;
import libclang.structs.CXString;
import libclang.values.CXFile;
import utils.LoggerUtils;

import java.lang.foreign.SegmentAllocator;

public class Utils {

    public static String cXString2String(CXString cxString) {
        Pointer<VI8<Byte>> str = LibclangFunctions.clang_getCString$Pointer(cxString);
        return new NString(str).reinterpretSize(Long.MAX_VALUE).toString();
    }

    public static void printLocation(SegmentAllocator mem, CXCursor cursor) {
        CXSourceLocation location = LibclangFunctions.clang_getCursorLocation$CXSourceLocation(mem, cursor);
        VPointerList<CXFile> file = CXFile.list(mem, 1);
        VI32List<VI32<Integer>> line = VI32.list(mem, 1);
        VI32List<VI32<Integer>> column = VI32.list(mem, 1);
        VI32List<VI32<Integer>> offset = VI32.list(mem, 1);
        LibclangFunctions.clang_getFileLocation(location, file, line, column, offset);
        CXString path = LibclangFunctions.clang_File_tryGetRealPathName$CXString(mem, file.getFirst());
        LoggerUtils.debug("Processing path " + Utils.cXString2String(path) + " line " + line.getFirst() + " column " + column.getFirst() + " offset " + offset.getFirst());
    }
}
