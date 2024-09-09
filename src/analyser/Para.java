package analyser;

import analyser.types.Type;

public record Para(Type paraType, String paraName) {
    @Override
    public String toString() {
        return "Para[" +
                "paraType=" + "[hidden]" + ", " +
                "paraName=" + paraName + ']';
    }
}