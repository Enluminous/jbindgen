import analyser.Analyser;
import analyser.Declare;
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

        for (Declare varDeclare : analyser.getVarDeclares()) {
            System.err.println(varDeclare.toString());
        }

        for (Function function : analyser.getFunctions()) {
            System.err.println(function);
        }

        analyser.close();
        System.err.println("Hello world!");

        HashMap<String, Type> types = analyser.getTypePool().getTypes();

    }
}