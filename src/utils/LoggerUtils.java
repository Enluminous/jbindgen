package utils;

import java.util.Date;

public class LoggerUtils {
    //                                      time        Level  logger thread  message
    private static final String FORMAT = "[%1$tF %1$tT] [%2$s] [%3$s] [%4$s] %5$s\n";
    public static final Logger GLOBAL_LOGGER = new Logger("Global");

    public static boolean DEBUG = false;


    public static void debug(Object msg) {
        GLOBAL_LOGGER.debug(msg);
    }

    public static void info(Object msg) {
        GLOBAL_LOGGER.info(msg);
    }

    public static void warning(Object msg) {
        GLOBAL_LOGGER.warning(msg);
    }

    public static void error(Object msg) {
        GLOBAL_LOGGER.error(msg);
    }

    public static final class Logger {
        private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        private final String name;

        private Logger(String name) {
            this.name = name;
        }

        public Logger(Class<?> c) {
            this(c.getCanonicalName());
        }

        public Logger() {
            this(STACK_WALKER.getCallerClass().getName());
        }

        public void debug(Object msg) {
            if (DEBUG)
                System.err.printf(FORMAT, new Date(), "D", name, Thread.currentThread().getName(), msg);
        }

        public void info(Object msg) {
            System.err.printf(FORMAT, new Date(), "I", name, Thread.currentThread().getName(), msg);
        }

        public void warning(Object msg) {
            System.err.printf(FORMAT, new Date(), "W", name, Thread.currentThread().getName(), msg);
        }

        public void error(Object msg) {
            System.err.printf(FORMAT, new Date(), "E", name, Thread.currentThread().getName(), msg);
        }
    }
}
