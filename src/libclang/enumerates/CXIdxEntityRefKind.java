package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXIdxEntityRefKind implements I32Op<CXIdxEntityRefKind>, Info<CXIdxEntityRefKind> {
    public static final Info.Operations<CXIdxEntityRefKind> OPERATIONS = I32Op.makeOperations(CXIdxEntityRefKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXIdxEntityRefKind(int val) {
        this.val = val;
    }

    public static Array<CXIdxEntityRefKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXIdxEntityRefKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXIdxEntityRefKind> getOperations() {
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

    public static String enumToString(CXIdxEntityRefKind e) {
        return Utils.enumToString(CXIdxEntityRefKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXIdxEntityRefKind that && that.val == val;
    }

    public static final CXIdxEntityRefKind CXIdxEntityRef_Direct = new CXIdxEntityRefKind(1);
    public static final CXIdxEntityRefKind CXIdxEntityRef_Implicit = new CXIdxEntityRefKind(2);
}