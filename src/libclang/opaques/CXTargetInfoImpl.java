package libclang.opaques;
import libclang.common.Info;


public class CXTargetInfoImpl {
    private CXTargetInfoImpl() {
        throw new UnsupportedOperationException();
    }

    public static final Info.Operations<CXTargetInfoImpl> OPERATIONS = Info.makeOperations();
}
