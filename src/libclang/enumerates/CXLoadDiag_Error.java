package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXLoadDiag_Error implements I32Op<CXLoadDiag_Error>, Info<CXLoadDiag_Error> {
    public static final Info.Operations<CXLoadDiag_Error> OPERATIONS = I32Op.makeOperations(CXLoadDiag_Error::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXLoadDiag_Error(int val) {
        this.val = val;
    }

    public static Array<CXLoadDiag_Error> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXLoadDiag_Error> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXLoadDiag_Error> getOperations() {
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

    public static String enumToString(CXLoadDiag_Error e) {
        return Utils.enumToString(CXLoadDiag_Error.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXLoadDiag_Error that && that.val == val;
    }

    public static final CXLoadDiag_Error CXLoadDiag_None = new CXLoadDiag_Error(0);
    public static final CXLoadDiag_Error CXLoadDiag_Unknown = new CXLoadDiag_Error(1);
    public static final CXLoadDiag_Error CXLoadDiag_CannotLoad = new CXLoadDiag_Error(2);
    public static final CXLoadDiag_Error CXLoadDiag_InvalidFile = new CXLoadDiag_Error(3);
}