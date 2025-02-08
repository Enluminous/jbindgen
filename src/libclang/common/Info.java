package libclang.common;


import java.lang.foreign.MemorySegment;
import libclang.common.Operation;


public interface Info<T> extends Operation {
    interface InfoOp<T> {
        Operations<T> getOperations();
    }

    record Operations<T>(Constructor<? extends T, MemorySegment> constructor, Copy<? super T> copy, long byteSize) {
        public interface Constructor<R, P> {
            R create(P param, long offset);
        }

        public interface Copy<S> {
            void copyTo(S source, MemorySegment dest, long offset);
        }
    }

    InfoOp<T> operator();

    static <I> Operations<I> makeOperations() {
        return new Operations<>((_, _) -> {
            throw new UnsupportedOperationException();
        }, (_, _, _) -> {
            throw new UnsupportedOperationException();
        }, 0);
    }
}
