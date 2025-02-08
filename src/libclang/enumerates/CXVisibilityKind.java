package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXVisibilityKind implements I32Op<CXVisibilityKind>, Info<CXVisibilityKind> {
    public static final Info.Operations<CXVisibilityKind> OPERATIONS = I32Op.makeOperations(CXVisibilityKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXVisibilityKind(int val) {
        this.val = val;
    }

    public static Array<CXVisibilityKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXVisibilityKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXVisibilityKind> getOperations() {
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

    public static String enumToString(CXVisibilityKind e) {
        return Utils.enumToString(CXVisibilityKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXVisibilityKind that && that.val == val;
    }

    public static final CXVisibilityKind CXVisibility_Invalid = new CXVisibilityKind(0);
    public static final CXVisibilityKind CXVisibility_Hidden = new CXVisibilityKind(1);
    public static final CXVisibilityKind CXVisibility_Protected = new CXVisibilityKind(2);
    public static final CXVisibilityKind CXVisibility_Default = new CXVisibilityKind(3);
}