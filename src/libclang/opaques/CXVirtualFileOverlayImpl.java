package libclang.opaques;
import libclang.common.Info;


public class CXVirtualFileOverlayImpl {
    private CXVirtualFileOverlayImpl() {
        throw new UnsupportedOperationException();
    }

    public static final Info.Operations<CXVirtualFileOverlayImpl> OPERATIONS = Info.makeOperations();
}
