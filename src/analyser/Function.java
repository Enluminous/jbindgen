package analyser;

import java.util.ArrayList;

public record Function(String name, TypePool.Type ret, ArrayList<Para> paras) {
    public Function(String name, TypePool.Type ret) {
        this(name, ret, new ArrayList<>());
    }

    public void addPara(Para para) {
        paras.add(para);
    }

    @Override
    public String toString() {
        return "Function{" +
                "name='" + name + '\'' +
                ", ret=" + ret +
                ", paras=" + paras +
                '}';
    }
}