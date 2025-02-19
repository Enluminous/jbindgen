package analyser;

import analyser.types.Type;

import java.util.ArrayList;

public record Function(String name, Type ret, ArrayList<Para> paras, String signature) {
    public Function(String name, Type ret, String signature) {
        this(name, ret, new ArrayList<>(), signature);
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