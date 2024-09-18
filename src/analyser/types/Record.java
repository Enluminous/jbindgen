package analyser.types;

import analyser.Para;

import java.util.ArrayList;

public sealed abstract class Record extends Type permits Struct, Union {
    protected String displayName;
    protected final ArrayList<Para> members = new ArrayList<>();


    public Record(String typeName, long sizeof) {
        super(typeName, sizeof);
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        if (displayName != null)
            return displayName;
        return typeName;
    }
}
