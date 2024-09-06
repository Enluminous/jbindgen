package analyser;

import libclang.LibclangFunctions;
import libclang.shared.NString;
import libclang.shared.Pointer;
import libclang.shared.values.VI8;
import libclang.structs.CXString;

public class Utils {

    public static String cXString2String(CXString cxString) {
        Pointer<VI8<Byte>> str = LibclangFunctions.clang_getCString$Pointer(cxString);
        return new NString(str).reinterpretSize(Long.MAX_VALUE).toString();
    }
}
