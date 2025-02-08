package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXGlobalOptFlags implements I32Op<CXGlobalOptFlags>, Info<CXGlobalOptFlags> {
    public static final Info.Operations<CXGlobalOptFlags> OPERATIONS = I32Op.makeOperations(CXGlobalOptFlags::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXGlobalOptFlags(int val) {
        this.val = val;
    }

    public static Array<CXGlobalOptFlags> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXGlobalOptFlags> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXGlobalOptFlags> getOperations() {
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

    public static String enumToString(CXGlobalOptFlags e) {
        return Utils.enumToString(CXGlobalOptFlags.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXGlobalOptFlags that && that.val == val;
    }

    public static final CXGlobalOptFlags CXGlobalOpt_None = new CXGlobalOptFlags(0);
    public static final CXGlobalOptFlags CXGlobalOpt_ThreadBackgroundPriorityForIndexing = new CXGlobalOptFlags(1);
    public static final CXGlobalOptFlags CXGlobalOpt_ThreadBackgroundPriorityForEditing = new CXGlobalOptFlags(2);
    public static final CXGlobalOptFlags CXGlobalOpt_ThreadBackgroundPriorityForAll = new CXGlobalOptFlags(3);
}