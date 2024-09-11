package analyser.types;

import analyser.Declare;

import java.util.ArrayList;

public final class Enum extends Type {
    private final ArrayList<Declare> declares = new ArrayList<>();

    public Enum(String typeName) {
        super(typeName);
    }

    public void addDeclare(Declare declare) {
        declares.add(declare);
    }

    @Override
    public String toString() {
        return "Enum{" +
                "declares=" + declares +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
