package analyser.types;

import analyser.Para;

import java.util.ArrayList;

public final class TypeFunction extends Type {
    private final Type ret;
    private final ArrayList<Para> paras;

    public TypeFunction(String typeName, Type ret, ArrayList<Para> paras, String location, long align) {
        super(typeName, 0, location, align);
        this.ret = ret;
        this.paras = paras;
    }

    public Type getRet() {
        return ret;
    }

    public ArrayList<Para> getParas() {
        return paras;
    }

    @Override
    public String toString() {
        return "TypeFunction{" +
                "ret=" + ret +
                ", paras=" + paras +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
