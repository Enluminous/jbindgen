package analyser.types;

import analyser.Para;
import analyser.TypePool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public final class Struct extends Type {
    private final ArrayList<Para> paras = new ArrayList<>();

    public Struct(String name) {
        super(name);
    }

    public void addPara(Para para) {
        paras.add(para);
    }

    public void addParas(Collection<Para> ps) {
        paras.addAll(ps);
    }

    public ArrayList<Para> getParas() {
        return paras;
    }

    public Struct setName(String name) {
        Struct s = new Struct(name);
        s.addParas(paras);
        return s;
    }

    @Override
    public String toString() {
        return "Struct{" +
                "paras=" + paras +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Struct struct = (Struct) o;
        return Objects.equals(paras, struct.paras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), paras);
    }
}
