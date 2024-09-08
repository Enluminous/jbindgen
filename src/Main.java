import analyser.Analyser;
import analyser.Function;
import analyser.Struct;
import libclang.*;

import java.lang.foreign.Arena;
import java.lang.foreign.SymbolLookup;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LibclangSymbols.addSymbols(SymbolLookup.libraryLookup("libclang-17.so.1", Arena.global()));
        var analyser = new Analyser("test/miniaudio.h", List.of("-I", "/usr/include"));
        for (Struct struct : analyser.getStructs()) {
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
    }
}