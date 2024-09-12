package analyser;

import analyser.types.Type;

import java.util.ArrayList;


//TODO: include TypeFunction?
public record Function(String name, Type ret, ArrayList<Para> paras) {
    public Function(String name, Type ret) {
        this(name, ret, new ArrayList<>());
    }

    public void addPara(Para para) {
        paras.add(para);
    }

    @Override
    public Type ret() {
        return ret;
    }

    @Override
    public ArrayList<Para> paras() {
        return paras;
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