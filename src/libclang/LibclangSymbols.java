package libclang;

import libclang.shared.FunctionUtils;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Optional;

public class LibclangSymbols {
    private LibclangSymbols() {
        throw new UnsupportedOperationException();
    }

    private static final ArrayList<SymbolLookup> symbolLookups = new ArrayList<>();
    private static final boolean critical = false;

    public static void addSymbols(SymbolLookup symbolLookup) {
        symbolLookups.add(symbolLookup);
    }

    public static ArrayList<SymbolLookup> getSymbolLookups() {
        return symbolLookups;
    }

    public static Optional<MethodHandle> toMethodHandle(String functionName, FunctionDescriptor functionDescriptor) {
        return symbolLookups.stream().map(symbolLookup -> FunctionUtils.toMethodHandle(symbolLookup, functionName, functionDescriptor, critical))
                .filter(Optional::isPresent).map(Optional::get).findFirst();
    }

    public static Optional<MemorySegment> getSymbol(String symbol) {
        return symbolLookups.stream().map(symbolLookup -> symbolLookup.find(symbol))
                .filter(Optional::isPresent).map(Optional::get).findFirst();
    }
}
