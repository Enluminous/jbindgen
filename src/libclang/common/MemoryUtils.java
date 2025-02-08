package libclang.common;


import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import java.util.Objects;

public final class MemoryUtils {
    public interface MemorySupport {
        void setByte(MemorySegment ms, long offset, byte val);

        void setShort(MemorySegment ms, long offset, short val);

        void setInt(MemorySegment ms, long offset, int val);

        void setLong(MemorySegment ms, long offset, long val);

        void setAddr(MemorySegment ms, long offset, MemorySegment val);

        void setFloat(MemorySegment ms, long offset, float val);

        void setDouble(MemorySegment ms, long offset, double val);

        byte getByte(MemorySegment ms, long offset);

        short getShort(MemorySegment ms, long offset);

        int getInt(MemorySegment ms, long offset);

        long getLong(MemorySegment ms, long offset);

        MemorySegment getAddr(MemorySegment ms, long offset);

        float getFloat(MemorySegment ms, long offset);

        double getDouble(MemorySegment ms, long offset);

        void memcpy(MemorySegment src, long srcOffset, MemorySegment dest, long destOffset, long byteSize);
    }

    public static MemorySupport ms = new MemorySupport() {
        @Override
        public void setByte(MemorySegment ms, long offset, byte val) {
            ms.set(ValueLayout.JAVA_BYTE, offset, val);
        }

        @Override
        public void setShort(MemorySegment ms, long offset, short val) {
            ms.set(ValueLayout.JAVA_SHORT, offset, val);
        }

        @Override
        public void setInt(MemorySegment ms, long offset, int val) {
            ms.set(ValueLayout.JAVA_INT, offset, val);
        }

        @Override
        public void setLong(MemorySegment ms, long offset, long val) {
            ms.set(ValueLayout.JAVA_LONG, offset, val);
        }

        @Override
        public void setAddr(MemorySegment ms, long offset, MemorySegment val) {
            ms.set(ValueLayout.ADDRESS, offset, val);
        }

        @Override
        public void setFloat(MemorySegment ms, long offset, float val) {
            ms.set(ValueLayout.JAVA_FLOAT, offset, val);
        }

        @Override
        public void setDouble(MemorySegment ms, long offset, double val) {
            ms.set(ValueLayout.JAVA_DOUBLE, offset, val);
        }

        @Override
        public byte getByte(MemorySegment ms, long offset) {
            return ms.get(ValueLayout.JAVA_BYTE, offset);
        }

        @Override
        public short getShort(MemorySegment ms, long offset) {
            return ms.get(ValueLayout.JAVA_SHORT, offset);
        }

        @Override
        public int getInt(MemorySegment ms, long offset) {
            return ms.get(ValueLayout.JAVA_INT, offset);
        }

        @Override
        public long getLong(MemorySegment ms, long offset) {
            return ms.get(ValueLayout.JAVA_LONG, offset);
        }

        @Override
        public MemorySegment getAddr(MemorySegment ms, long offset) {
            return ms.get(ValueLayout.ADDRESS, offset);
        }

        @Override
        public float getFloat(MemorySegment ms, long offset) {
            return ms.get(ValueLayout.JAVA_FLOAT, offset);
        }

        @Override
        public double getDouble(MemorySegment ms, long offset) {
            return ms.get(ValueLayout.JAVA_DOUBLE, offset);
        }

        @Override
        public void memcpy(MemorySegment src, long srcOffset, MemorySegment dest, long destOffset, long byteSize) {
            MemorySegment.copy(src, srcOffset, dest, destOffset, byteSize);
        }
    };

    private static final class MemorySupportHolder {
        private static final MemorySupport MEMORY_SUPPORT = Objects.requireNonNull(ms);
    }

    public static void setByte(MemorySegment ms, long offset, byte val) {
        MemorySupportHolder.MEMORY_SUPPORT.setByte(ms, offset, val);
    }

    public static void setShort(MemorySegment ms, long offset, short val) {
        MemorySupportHolder.MEMORY_SUPPORT.setShort(ms, offset, val);
    }

    public static void setInt(MemorySegment ms, long offset, int val) {
        MemorySupportHolder.MEMORY_SUPPORT.setInt(ms, offset, val);
    }

    public static void setLong(MemorySegment ms, long offset, long val) {
        MemorySupportHolder.MEMORY_SUPPORT.setLong(ms, offset, val);
    }

    public static void setAddr(MemorySegment ms, long offset, MemorySegment val) {
        MemorySupportHolder.MEMORY_SUPPORT.setAddr(ms, offset, val);
    }

    public static void setFloat(MemorySegment ms, long offset, float val) {
        MemorySupportHolder.MEMORY_SUPPORT.setFloat(ms, offset, val);
    }

    public static void setDouble(MemorySegment ms, long offset, double val) {
        MemorySupportHolder.MEMORY_SUPPORT.setDouble(ms, offset, val);
    }

    public static byte getByte(MemorySegment ms, long offset) {
        return MemorySupportHolder.MEMORY_SUPPORT.getByte(ms, offset);
    }

    public static short getShort(MemorySegment ms, long offset) {
        return MemorySupportHolder.MEMORY_SUPPORT.getShort(ms, offset);
    }

    public static int getInt(MemorySegment ms, long offset) {
        return MemorySupportHolder.MEMORY_SUPPORT.getInt(ms, offset);
    }

    public static long getLong(MemorySegment ms, long offset) {
        return MemorySupportHolder.MEMORY_SUPPORT.getLong(ms, offset);
    }

    public static MemorySegment getAddr(MemorySegment ms, long offset) {
        return MemorySupportHolder.MEMORY_SUPPORT.getAddr(ms, offset);
    }

    public static float getFloat(MemorySegment ms, long offset) {
        return MemorySupportHolder.MEMORY_SUPPORT.getFloat(ms, offset);
    }

    public static double getDouble(MemorySegment ms, long offset) {
        return MemorySupportHolder.MEMORY_SUPPORT.getDouble(ms, offset);
    }

    public static void memcpy(MemorySegment src, long srcOffset, MemorySegment dest, long destOffset, long byteSize) {
        MemorySupportHolder.MEMORY_SUPPORT.memcpy(src, srcOffset, dest, destOffset, byteSize);
    }
}
