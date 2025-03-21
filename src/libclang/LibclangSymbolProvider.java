package libclang;


import libclang.common.Utils;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Optional;

public class LibclangSymbolProvider {
    private LibclangSymbolProvider() {
        throw new UnsupportedOperationException();
    }

    private static final ArrayList<SymbolLookup> symbolLookups = new ArrayList<>();
    private static final boolean critical = false;

    public static void addSymbols(SymbolLookup symbolLookup) {
        symbolLookups.add(symbolLookup);
    }

    public static Optional<MethodHandle> downcallHandle(String functionName, FunctionDescriptor functionDescriptor) {
        return symbolLookups.stream().map(symbolLookup -> Utils.downcallHandle(symbolLookup, functionName, functionDescriptor, critical))
                .filter(Optional::isPresent).map(Optional::get).findFirst();
    }
}
