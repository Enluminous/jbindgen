package generator.config;

public class Config {
    interface PkgProvider {
        PackagePath getPackagePath();
    }

    Common commonCfg;

    static class Common {
        public final PkgProvider list;
        public final PkgProvider value;

        Common(PackagePath pkg) {
            list = () -> pkg.add("lists");
            value = () -> pkg.add("values");
        }
    }
}
