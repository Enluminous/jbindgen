package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXIdxEntityCXXTemplateKind implements I32Op<CXIdxEntityCXXTemplateKind>, Info<CXIdxEntityCXXTemplateKind> {
    public static final Info.Operations<CXIdxEntityCXXTemplateKind> OPERATIONS = I32Op.makeOperations(CXIdxEntityCXXTemplateKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXIdxEntityCXXTemplateKind(int val) {
        this.val = val;
    }

    public static Array<CXIdxEntityCXXTemplateKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXIdxEntityCXXTemplateKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXIdxEntityCXXTemplateKind> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Integer value() {
                return val;
            }
        };
    }

    private String str;

    @Override
    public String toString() {
        if (str == null) {
            str = enumToString(this);
            if (str == null) str = String.valueOf(val);
        }
        return str;
    }

    public static String enumToString(CXIdxEntityCXXTemplateKind e) {
        return Utils.enumToString(CXIdxEntityCXXTemplateKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXIdxEntityCXXTemplateKind that && that.val == val;
    }

    public static final CXIdxEntityCXXTemplateKind CXIdxEntity_NonTemplate = new CXIdxEntityCXXTemplateKind(0);
    public static final CXIdxEntityCXXTemplateKind CXIdxEntity_Template = new CXIdxEntityCXXTemplateKind(1);
    public static final CXIdxEntityCXXTemplateKind CXIdxEntity_TemplatePartialSpecialization = new CXIdxEntityCXXTemplateKind(2);
    public static final CXIdxEntityCXXTemplateKind CXIdxEntity_TemplateSpecialization = new CXIdxEntityCXXTemplateKind(3);
}