package generator.types.operations;

import java.util.List;

public interface MemoryOperation {

    record Setter(String paraType, String codeSegment) {

    }

    record Getter(String paraType, String ret, String codeSegment) {
    }

    /**
     * return the string that construct the type
     */
    List<Getter> getter(String ms, long offset);

    /**
     * return the string that copy the type to memory segment
     */
    List<Setter> setter(String ms, long offset, String varName);
}
