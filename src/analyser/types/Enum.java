package analyser.types;

import analyser.Declare;

import java.util.ArrayList;

public final class Enum extends Type {
    private final ArrayList<Declare> declares = new ArrayList<>();
    private final boolean unnamed;

    public Enum(String typeName, boolean unnamed) {
        super(typeName);
        this.unnamed = unnamed;
    }

    public boolean isUnnamed() {
        return unnamed;
    }

    public void addDeclare(Declare declare) {
        declares.add(declare);
    }

    public ArrayList<Declare> getDeclares() {
        return declares;
    }

    @Override
    public String toString() {
        return "Enum{" +
                "declares=" + declares +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
