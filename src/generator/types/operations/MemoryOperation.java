package generator.types.operations;

import generator.types.TypeImports;

public interface MemoryOperation {

    record Setter(String para, String codeSegment, TypeImports imports) {

    }

    record Getter(String para, String ret, String codeSegment, TypeImports imports) {
    }

    /**
     * return the string that construct the type
     */
    Getter getter(String ms, long offset);

    /**
     * return the string that copy the type to memory segment
     */
    Setter setter(String ms, long offset, String varName);
}
