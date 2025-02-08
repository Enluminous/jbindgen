package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXTypeNullabilityKind implements I32Op<CXTypeNullabilityKind>, Info<CXTypeNullabilityKind> {
    public static final Info.Operations<CXTypeNullabilityKind> OPERATIONS = I32Op.makeOperations(CXTypeNullabilityKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXTypeNullabilityKind(int val) {
        this.val = val;
    }

    public static Array<CXTypeNullabilityKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXTypeNullabilityKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXTypeNullabilityKind> getOperations() {
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

    public static String enumToString(CXTypeNullabilityKind e) {
        return Utils.enumToString(CXTypeNullabilityKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXTypeNullabilityKind that && that.val == val;
    }

    public static final CXTypeNullabilityKind CXTypeNullability_NonNull = new CXTypeNullabilityKind(0);
    public static final CXTypeNullabilityKind CXTypeNullability_Nullable = new CXTypeNullabilityKind(1);
    public static final CXTypeNullabilityKind CXTypeNullability_Unspecified = new CXTypeNullabilityKind(2);
    public static final CXTypeNullabilityKind CXTypeNullability_Invalid = new CXTypeNullabilityKind(3);
    public static final CXTypeNullabilityKind CXTypeNullability_NullableResult = new CXTypeNullabilityKind(4);
}