package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXChoice implements I32Op<CXChoice>, Info<CXChoice> {
    public static final Info.Operations<CXChoice> OPERATIONS = I32Op.makeOperations(CXChoice::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXChoice(int val) {
        this.val = val;
    }

    public static Array<CXChoice> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXChoice> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXChoice> getOperations() {
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

    public static String enumToString(CXChoice e) {
        return Utils.enumToString(CXChoice.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXChoice that && that.val == val;
    }

    public static final CXChoice CXChoice_Default = new CXChoice(0);
    public static final CXChoice CXChoice_Enabled = new CXChoice(1);
    public static final CXChoice CXChoice_Disabled = new CXChoice(2);
}