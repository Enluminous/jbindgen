package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXCompletionChunkKind implements I32Op<CXCompletionChunkKind>, Info<CXCompletionChunkKind> {
    public static final Info.Operations<CXCompletionChunkKind> OPERATIONS = I32Op.makeOperations(CXCompletionChunkKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXCompletionChunkKind(int val) {
        this.val = val;
    }

    public static Array<CXCompletionChunkKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXCompletionChunkKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXCompletionChunkKind> getOperations() {
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

    public static String enumToString(CXCompletionChunkKind e) {
        return Utils.enumToString(CXCompletionChunkKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXCompletionChunkKind that && that.val == val;
    }

    public static final CXCompletionChunkKind CXCompletionChunk_Optional = new CXCompletionChunkKind(0);
    public static final CXCompletionChunkKind CXCompletionChunk_TypedText = new CXCompletionChunkKind(1);
    public static final CXCompletionChunkKind CXCompletionChunk_Text = new CXCompletionChunkKind(2);
    public static final CXCompletionChunkKind CXCompletionChunk_Placeholder = new CXCompletionChunkKind(3);
    public static final CXCompletionChunkKind CXCompletionChunk_Informative = new CXCompletionChunkKind(4);
    public static final CXCompletionChunkKind CXCompletionChunk_CurrentParameter = new CXCompletionChunkKind(5);
    public static final CXCompletionChunkKind CXCompletionChunk_LeftParen = new CXCompletionChunkKind(6);
    public static final CXCompletionChunkKind CXCompletionChunk_RightParen = new CXCompletionChunkKind(7);
    public static final CXCompletionChunkKind CXCompletionChunk_LeftBracket = new CXCompletionChunkKind(8);
    public static final CXCompletionChunkKind CXCompletionChunk_RightBracket = new CXCompletionChunkKind(9);
    public static final CXCompletionChunkKind CXCompletionChunk_LeftBrace = new CXCompletionChunkKind(10);
    public static final CXCompletionChunkKind CXCompletionChunk_RightBrace = new CXCompletionChunkKind(11);
    public static final CXCompletionChunkKind CXCompletionChunk_LeftAngle = new CXCompletionChunkKind(12);
    public static final CXCompletionChunkKind CXCompletionChunk_RightAngle = new CXCompletionChunkKind(13);
    public static final CXCompletionChunkKind CXCompletionChunk_Comma = new CXCompletionChunkKind(14);
    public static final CXCompletionChunkKind CXCompletionChunk_ResultType = new CXCompletionChunkKind(15);
    public static final CXCompletionChunkKind CXCompletionChunk_Colon = new CXCompletionChunkKind(16);
    public static final CXCompletionChunkKind CXCompletionChunk_SemiColon = new CXCompletionChunkKind(17);
    public static final CXCompletionChunkKind CXCompletionChunk_Equal = new CXCompletionChunkKind(18);
    public static final CXCompletionChunkKind CXCompletionChunk_HorizontalSpace = new CXCompletionChunkKind(19);
    public static final CXCompletionChunkKind CXCompletionChunk_VerticalSpace = new CXCompletionChunkKind(20);
}