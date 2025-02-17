package utils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import analyser.Analyser;
import generator.PackagePath;
import libclang.common.Str;
import processor.Processor;
import processor.Utils;

public class CmdLineParser {
    private static class Component {
        public String header;
        public String filterString;
        public List<String> includes;
        public boolean analyseMacro;
        public Path outDir;
        public String libPkg;
        public String libName;

        public boolean finished() {
            return header != null &&
                    filterString != null &&
                    includes != null &&
                    outDir != null &&
                    libPkg != null &&
                    libName != null;
        }

        @Override
        public String toString() {
            return "Component{" +
                    "header='" + header + '\'' +
                    ", filterString='" + filterString + '\'' +
                    ", includes=" + includes +
                    ", analyseMacro=" + analyseMacro +
                    ", outDir=" + outDir +
                    ", libPkg='" + libPkg + '\'' +
                    ", libName='" + libName + '\'' +
                    '}';
        }
    }

    private static void printHelp() {
        System.out.println("Usage:");
        System.out.println("--header=<header_file_path>");
        System.out.println("--includes=<include_path1>;<include_path2>;...");
        System.out.println("--enable-macro=<true|false>");
        System.out.println("--out=<output_directory>");
        System.out.println("--pkg-name=<package_name>");
        System.out.println("--name=<library_name>");
        System.out.println("--filter-str=<filter_string>");
    }

    private static List<Component> parse(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException();
        }
        List<Component> components = new ArrayList<>();
        Component current = new Component();
        for (String arg : args) {
            if (current.finished()) {
                components.add(current);
                current = new Component();
                System.out.println("Note: creating new extra component");
            }

            var kv = arg.split("=");
            if (kv.length != 2) {
                throw new IllegalArgumentException("Invalid argument:" + arg + ", should be --key=value");
            }
            var key = kv[0];
            var value = kv[1];

            switch (key) {
                case "--header":
                    current.header = value;
                    break;
                case "--includes":
                    current.includes = Arrays.stream(value.split(";")).toList();
                    break;
                case "--enable-macro":
                    current.analyseMacro = Boolean.parseBoolean(value);
                    break;
                case "--out":
                    current.outDir = Path.of(value);
                    break;
                case "--pkg-name":
                    current.libPkg = value;
                    break;
                case "--name":
                    current.libName = value;
                    break;
                case "--filter-str":
                    current.filterString = value;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid argument:" + arg);
            }
        }
        if (!current.finished())
            throw new RuntimeException("Incomplete argument detected: " + current);
        components.add(current);
        return components;
    }

    private static boolean checkArgs(String[] args) {
        if (args.length == 0) {
            printHelp();
            return false;
        }
        for (String arg : args) {
            if (arg.equals("--help") || arg.equals("-h")) {
                printHelp();
                return false;
            }
        }
        return true;
    }

    public static void solveAndGen(String[] args) {
        if (!checkArgs(args))
            return;

        List<Component> components = parse(args);
        System.out.println("Parsed " + components.size() + " component(s):");
        for (Component component : components) {
            System.out.println(component);
        }

        var it = components.iterator();
        Component primary = it.next();
        Analyser primaryAnalyser = new Analyser(primary.header, primary.includes, primary.analyseMacro);
        primaryAnalyser.close();
        Processor primaryProc = new Processor(primaryAnalyser,
                Utils.DestinationProvider.ofDefault(new PackagePath(primary.outDir).add(primary.libPkg), primary.libName),
                Utils.Filter.ofDefault(s -> s.contains(primary.filterString)));

        while (it.hasNext()) {
            Component extra = it.next();
            Analyser analyser = new Analyser(extra.header, extra.includes, extra.analyseMacro);
            analyser.close();
            primaryProc = primaryProc.withExtra(analyser,
                    Utils.DestinationProvider.ofDefault(new PackagePath(extra.outDir).add(extra.libPkg), extra.libName),
                    Utils.Filter.ofDefault(s -> s.contains(extra.filterString)));
        }
        primaryProc.generate();
    }
}
