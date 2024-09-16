package analyser.types;

import analyser.Para;

import java.util.ArrayList;

public sealed abstract class Record extends Type permits Struct, Union {
    protected String displayName;
    protected long sizeof;
    protected final ArrayList<Para> members = new ArrayList<>();


    public Record(String typeName, long sizeof) {
        super(typeName);
        this.sizeof = sizeof;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getSizeof() {
        return sizeof;
    }

    public String getDisplayName() {
        if (displayName != null)
            return displayName;
        return typeName;
    }
}
