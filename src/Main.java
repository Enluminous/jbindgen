import analyser.Analyser;
import generator.PackagePath;
import libclang.LibclangSymbols;
import preprocessor.Preprocessor;
import utils.CommonUtils;

import java.lang.foreign.Arena;
import java.lang.foreign.SymbolLookup;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CommonUtils.disableClangCrashRecovery();
        LibclangSymbols.addSymbols(SymbolLookup.libraryLookup("libclang-17.so.1", Arena.global()));
        var analyser = new Analyser("test/miniaudio.h", List.of("-I", "/usr/include"), true);
        new Preprocessor(analyser.getFunctions(), analyser.getMacros(), analyser.getVarDeclares(),analyser.getTypes(),
                Preprocessor.DestinationProvider.ofDefault(new PackagePath(Path.of("test-out/src")).add("libminiaudio"), "MiniAudio"));
        analyser.close();
        System.err.println("Hello world!");
    }
}