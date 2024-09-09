package analyser;

public record Para(TypePool.Type paraType, String paraName) {
    @Override
    public String toString() {
        return "Para[" +
                "paraType=" + "[hidden]" + ", " +
                "paraName=" + paraName + ']';
    }
}