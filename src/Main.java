import analyser.Analyser;
import libclang.*;

import java.lang.foreign.Arena;
import java.lang.foreign.SymbolLookup;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LibclangSymbols.addSymbols(SymbolLookup.libraryLookup("/lib/x86_64-linux-gnu/libclang-17.so.1", Arena.global()));
        new Analyser("/usr/lib/llvm-16/include/clang-c/Index.h", List.of("-I", "/usr/lib/llvm-16/include/", "-I", "/usr/include"));
        System.out.println("Hello world!");
    }
}