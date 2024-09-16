package analyser;

import analyser.types.Type;

import java.util.OptionalInt;
import java.util.OptionalLong;

public record Para(Type paraType, String paraName, OptionalLong offset, OptionalInt bitWidth) {
    @Override
    public String toString() {
        return "Para{" +
                "paraType=" + "[hidden]" +
                ", paraName='" + paraName + '\'' +
                ", offset=" + offset +
                ", bitWidth=" + bitWidth +
                '}';
    }
}