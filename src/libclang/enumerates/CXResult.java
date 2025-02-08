package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXResult implements I32Op<CXResult>, Info<CXResult> {
    public static final Info.Operations<CXResult> OPERATIONS = I32Op.makeOperations(CXResult::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXResult(int val) {
        this.val = val;
    }

    public static Array<CXResult> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXResult> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXResult> getOperations() {
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

    public static String enumToString(CXResult e) {
        return Utils.enumToString(CXResult.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXResult that && that.val == val;
    }

    public static final CXResult CXResult_Success = new CXResult(0);
    public static final CXResult CXResult_Invalid = new CXResult(1);
    public static final CXResult CXResult_VisitBreak = new CXResult(2);
}