import analyser.Analyser;
import analyser.Function;
import analyser.types.*;
import generator.Generator;
import libclang.*;

import java.lang.foreign.Arena;
import java.lang.foreign.SymbolLookup;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        LibclangSymbols.addSymbols(SymbolLookup.libraryLookup("libclang-17.so.1", Arena.global()));
        var analyser = new Analyser("test/miniaudio.h", List.of("-I", "/usr/include"));
        for (analyser.Struct struct : analyser.getStructs()) {
            System.out.println(struct);
        }
        analyser.getTypePool().getTypes().forEach((k, v) -> {
            System.out.println("Type Pool: " + v);
        });

        for (Function function : analyser.getFunctions()) {
            System.out.println(function);
        }

        analyser.close();
        System.out.println("Hello world!");

        HashMap<String, Type> types = analyser.getTypePool().getTypes();

        Generator generator = new Generator(analyser.getFunctions(), types, "test", Path.of("test-out/src/test"));
        generator.generate();
    }
}