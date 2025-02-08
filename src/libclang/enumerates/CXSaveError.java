package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXSaveError implements I32Op<CXSaveError>, Info<CXSaveError> {
    public static final Info.Operations<CXSaveError> OPERATIONS = I32Op.makeOperations(CXSaveError::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXSaveError(int val) {
        this.val = val;
    }

    public static Array<CXSaveError> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXSaveError> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXSaveError> getOperations() {
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

    public static String enumToString(CXSaveError e) {
        return Utils.enumToString(CXSaveError.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXSaveError that && that.val == val;
    }

    public static final CXSaveError CXSaveError_None = new CXSaveError(0);
    public static final CXSaveError CXSaveError_Unknown = new CXSaveError(1);
    public static final CXSaveError CXSaveError_TranslationErrors = new CXSaveError(2);
    public static final CXSaveError CXSaveError_InvalidTU = new CXSaveError(3);
}