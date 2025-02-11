import analyser.Analyser;
import generator.PackagePath;
import libclang.LibclangSymbolProvider;
import processor.Processor;
import processor.Utils;
import utils.CommonUtils;

import java.lang.foreign.Arena;
import java.lang.foreign.SymbolLookup;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CommonUtils.disableClangCrashRecovery();
        LibclangSymbolProvider.addSymbols(SymbolLookup.libraryLookup("libclang-17.so.1", Arena.global()));
        var analyser = new Analyser("test/miniaudio.h", List.of("-I", "/usr/include"), false);
        new Processor(analyser,
                Utils.DestinationProvider.ofDefault(new PackagePath(Path.of("test-out/src")).add("libminiaudio"), "MiniAudio"),
                Utils.Filter.ofDefault(s -> s.contains("miniaudio.h")))
                .generate();
        analyser.close();
        System.err.println("Hello world!");
    }
}