package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXNameRefFlags implements I32Op<CXNameRefFlags>, Info<CXNameRefFlags> {
    public static final Info.Operations<CXNameRefFlags> OPERATIONS = I32Op.makeOperations(CXNameRefFlags::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXNameRefFlags(int val) {
        this.val = val;
    }

    public static Array<CXNameRefFlags> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXNameRefFlags> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXNameRefFlags> getOperations() {
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

    public static String enumToString(CXNameRefFlags e) {
        return Utils.enumToString(CXNameRefFlags.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXNameRefFlags that && that.val == val;
    }

    public static final CXNameRefFlags CXNameRange_WantQualifier = new CXNameRefFlags(1);
    public static final CXNameRefFlags CXNameRange_WantTemplateArgs = new CXNameRefFlags(2);
    public static final CXNameRefFlags CXNameRange_WantSinglePiece = new CXNameRefFlags(4);
}