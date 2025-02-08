package libclang.common;


import libclang.common.Array;
import libclang.common.ArrayOp;
import libclang.common.I8;
import libclang.common.Info;
import libclang.common.Ptr;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class Str extends ArrayOp.AbstractRandomAccessList<I8> implements ArrayOp<Str, I8>, Info<Str> {
    public static final long BYTE_SIZE = ValueLayout.ADDRESS.byteSize();
    public static final Info.Operations<Str> OPERATIONS = new Info.Operations<>(
            (param, offset) -> new Str(fitByteSize(param.get(ValueLayout.ADDRESS, offset))),
            (source, dest, offset) -> dest.set(ValueLayout.ADDRESS, offset, source.ptr), BYTE_SIZE);
    private final MemorySegment ptr;

    @Override
    public I8 get(int index) {
        return new I8(ptr.getAtIndex(ValueLayout.JAVA_BYTE, index));
    }

    private static Array<Str> makeArray(SegmentAllocator allocator, Stream<String> ss) {
        List<Str> list = ss.map(s -> new Str(allocator, s)).toList();
        return new Array<>(allocator, list.getFirst(), list);
    }

    private static final long HIMAGIC_FOR_BYTES = 0x8080_8080_8080_8080L;
    private static final long LOMAGIC_FOR_BYTES = 0x0101_0101_0101_0101L;

    private static boolean containZeroByte(long l) {
        return ((l - LOMAGIC_FOR_BYTES) & (~l) & HIMAGIC_FOR_BYTES) != 0;
    }

    private static int strlen(MemorySegment segment) {
        int count = 0;
        while (!containZeroByte(segment.get(ValueLayout.JAVA_LONG_UNALIGNED, count))) {
            count += 4;
        }
        while (segment.get(ValueLayout.JAVA_BYTE, count) != 0) {
            segment.get(ValueLayout.JAVA_BYTE, count);
            count++;
        }
        return count;
    }

    private static MemorySegment fitByteSize(MemorySegment segment) {
        return segment.address() == 0 ? segment : segment.reinterpret(1 + strlen(segment.reinterpret(Long.MAX_VALUE)));
    }


    public static Array<Str> list(SegmentAllocator allocator, String[] strings) {
        return makeArray(allocator, Arrays.stream(strings));
    }

    public static Array<Str> list(SegmentAllocator allocator, Collection<String> strings) {
        return makeArray(allocator, strings.stream());
    }

    protected Str(MemorySegment ptr) {
        this.ptr = ptr;
    }

    public Str(PtrI<? extends I8I<?>> ptr) {
        this(fitByteSize(ptr.operator().value()));
    }

    public Str(SegmentAllocator allocator, String s) {
        this(allocator.allocateFrom(s, StandardCharsets.UTF_8));
    }

    public String get() {
        return MemorySegment.NULL.address() == ptr.address() ? null : toString();
    }

    @Override
    public int size() {
        return (int) ptr.byteSize();
    }

    @Override
    public String toString() {
        return MemorySegment.NULL.address() == ptr.address()
                ? "Str{ptr=" + ptr + '}'
                : ptr.getString(0, StandardCharsets.UTF_8);
    }

    @Override
    public ArrayOpI<Str, I8> operator() {
        return new ArrayOpI<>() {
            @Override
            public Info.Operations<I8> elementOperation() {
                return I8.OPERATIONS;
            }

            @Override
            public Str reinterpret(long length) {
                return new Str(ptr.reinterpret(length));
            }

            @Override
            public Ptr<I8> pointerAt(long index) {
                return new Ptr<>(ptr.asSlice(index, 1), I8.OPERATIONS);
            }

            @Override
            public Info.Operations<Str> getOperations() {
                return OPERATIONS;
            }

            @Override
            public I8 pointee() {
                return get(0);
            }

            @Override
            public void setPointee(I8 pointee) {
                set(0, pointee);
            }

            @Override
            public List<Ptr<I8>> pointerList() {
                return new ArrayOp.AbstractRandomAccessList<>() {
                    @Override
                    public Ptr<I8> get(int index) {
                        return pointerAt(index);
                    }

                    @Override
                    public int size() {
                        return Str.this.size();
                    }
                };
            }

            @Override
            public MemorySegment value() {
                return ptr;
            }
        };
    }
}
