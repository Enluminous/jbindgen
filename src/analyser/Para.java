package analyser;

import analyser.types.Type;

import java.util.OptionalLong;

public record Para(Type paraType, String paraName, OptionalLong offset, OptionalLong bitWidth, OptionalLong align) {
    @Override
    public String toString() {
        return "Para{" +
                "paraType=" + "[hidden]" +
                ", paraName='" + paraName + '\'' +
                ", offset=" + offset +
                ", bitWidth=" + bitWidth +
                ", align=" + align +
                '}';
    }
}