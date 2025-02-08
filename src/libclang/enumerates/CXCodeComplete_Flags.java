package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXCodeComplete_Flags implements I32Op<CXCodeComplete_Flags>, Info<CXCodeComplete_Flags> {
    public static final Info.Operations<CXCodeComplete_Flags> OPERATIONS = I32Op.makeOperations(CXCodeComplete_Flags::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXCodeComplete_Flags(int val) {
        this.val = val;
    }

    public static Array<CXCodeComplete_Flags> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXCodeComplete_Flags> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXCodeComplete_Flags> getOperations() {
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

    public static String enumToString(CXCodeComplete_Flags e) {
        return Utils.enumToString(CXCodeComplete_Flags.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXCodeComplete_Flags that && that.val == val;
    }

    public static final CXCodeComplete_Flags CXCodeComplete_IncludeMacros = new CXCodeComplete_Flags(1);
    public static final CXCodeComplete_Flags CXCodeComplete_IncludeCodePatterns = new CXCodeComplete_Flags(2);
    public static final CXCodeComplete_Flags CXCodeComplete_IncludeBriefComments = new CXCodeComplete_Flags(4);
    public static final CXCodeComplete_Flags CXCodeComplete_SkipPreamble = new CXCodeComplete_Flags(8);
    public static final CXCodeComplete_Flags CXCodeComplete_IncludeCompletionsWithFixIts = new CXCodeComplete_Flags(16);
}