package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXVisitorResult implements I32Op<CXVisitorResult>, Info<CXVisitorResult> {
    public static final Info.Operations<CXVisitorResult> OPERATIONS = I32Op.makeOperations(CXVisitorResult::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXVisitorResult(int val) {
        this.val = val;
    }

    public static Array<CXVisitorResult> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXVisitorResult> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXVisitorResult> getOperations() {
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

    public static String enumToString(CXVisitorResult e) {
        return Utils.enumToString(CXVisitorResult.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXVisitorResult that && that.val == val;
    }

    public static final CXVisitorResult CXVisit_Break = new CXVisitorResult(0);
    public static final CXVisitorResult CXVisit_Continue = new CXVisitorResult(1);
}