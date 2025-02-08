package libclang.opaques;
import libclang.common.Info;
public class Void {
    private Void() {
        throw new UnsupportedOperationException();
    }

    public static final Info.Operations<Void> OPERATIONS = Info.makeOperations();
}
