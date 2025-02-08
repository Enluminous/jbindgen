package libclang.opaques;
import libclang.common.Info;


public class CXTranslationUnitImpl {
    private CXTranslationUnitImpl() {
        throw new UnsupportedOperationException();
    }

    public static final Info.Operations<CXTranslationUnitImpl> OPERATIONS = Info.makeOperations();
}
