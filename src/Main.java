import java.lang.foreign.Arena;
import java.lang.foreign.SymbolLookup;

import libclang.LibclangSymbolProvider;
import utils.CmdLineParser;
import utils.CommonUtils;

public class Main {
    public static void main(String[] args) {
        CommonUtils.disableClangCrashRecovery();
        LibclangSymbolProvider.addSymbols(SymbolLookup.libraryLookup("libclang-17.so.1", Arena.global()));
        CmdLineParser.solveAndGen(args);
    }
}