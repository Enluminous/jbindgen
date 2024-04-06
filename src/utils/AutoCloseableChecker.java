package utils;

import java.util.concurrent.ConcurrentHashMap;

import static utils.CommonUtils.Assert;


public class AutoCloseableChecker {
    private AutoCloseableChecker() {
        throw new UnsupportedOperationException();
    }

    private static final ConcurrentHashMap<AutoCloseable, String> SHOULD_CLOSE = new ConcurrentHashMap<>();

    public static void checkNonClosed() {
        SHOULD_CLOSE.forEach((_, s) -> System.err.println(STR."detected non closed:\n\{s}"));
    }

    public interface NonThrowAutoCloseable extends AutoCloseable {
        @Override
        void close();
    }

    public static class CheckedAutoCloseable implements AutoCloseable {
        public CheckedAutoCloseable() {
            SHOULD_CLOSE.put(this, CommonUtils.getStackString());
        }

        @Override
        public void close() {
            Assert(SHOULD_CLOSE.remove(this) != null, "called close but not register it");
        }
    }
}