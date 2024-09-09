package analyser.types;

import analyser.Para;

import java.util.ArrayList;

public class TypeFunction extends Type {
    private final Type ret;
    private final ArrayList<Para> paras;

    public TypeFunction(String typeName, Type ret, ArrayList<Para> paras) {
        super(typeName);
        this.ret = ret;
        this.paras = paras;
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
