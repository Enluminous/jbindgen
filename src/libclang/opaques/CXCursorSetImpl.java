package libclang.opaques;
import libclang.common.Info;


public class CXCursorSetImpl {
    private CXCursorSetImpl() {
        throw new UnsupportedOperationException();
    }

    public static final Info.Operations<CXCursorSetImpl> OPERATIONS = Info.makeOperations();
}
