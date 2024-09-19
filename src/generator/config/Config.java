package generator.config;

public class Config {
    public interface PkgProvider {
        PackagePath getPackagePath();
    }

    public Common commonCfg;


    public static class Common {
        public final PkgProvider list;
        public final PkgProvider value;
        public final PkgProvider symbolProvider;

        Common(PackagePath pkg, String libName) {
            list = () -> pkg.add("lists");
            value = () -> pkg.add("values");
            symbolProvider = () -> pkg.end(libName + "Symbols");
        }
    }
}
