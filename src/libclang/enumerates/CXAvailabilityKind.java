package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXAvailabilityKind implements I32Op<CXAvailabilityKind>, Info<CXAvailabilityKind> {
    public static final Info.Operations<CXAvailabilityKind> OPERATIONS = I32Op.makeOperations(CXAvailabilityKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXAvailabilityKind(int val) {
        this.val = val;
    }

    public static Array<CXAvailabilityKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXAvailabilityKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXAvailabilityKind> getOperations() {
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

    public static String enumToString(CXAvailabilityKind e) {
        return Utils.enumToString(CXAvailabilityKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXAvailabilityKind that && that.val == val;
    }

    public static final CXAvailabilityKind CXAvailability_Available = new CXAvailabilityKind(0);
    public static final CXAvailabilityKind CXAvailability_Deprecated = new CXAvailabilityKind(1);
    public static final CXAvailabilityKind CXAvailability_NotAvailable = new CXAvailabilityKind(2);
    public static final CXAvailabilityKind CXAvailability_NotAccessible = new CXAvailabilityKind(3);
}