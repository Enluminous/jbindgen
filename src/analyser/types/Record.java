package analyser.types;

public sealed abstract class Record extends Type permits Struct, Union {
    public Record(String typeName) {
        super(typeName);
    }

    protected String displayName;

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        if (displayName != null)
            return displayName;
        return typeName;
    }
}
