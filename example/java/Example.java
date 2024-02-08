import enluminous.natives.example.ExampleFunctions;
import enluminous.natives.example.ExampleMacros;
import enluminous.natives.example.ExampleSymbols;
import enluminous.natives.example.functions.ReadCallback;
import enluminous.natives.example.structs.Test;
import enluminous.natives.shared.NList;
import enluminous.natives.shared.values.VPointer;

import java.io.File;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

public class Example {
    public static void main(String[] args) {
        Arena mem = Arena.ofConfined();
        // add lookup
        ExampleSymbols.addSymbols(SymbolLookup.libraryLookup(new File("libexample.so").getAbsolutePath(), mem));

        // allocate a test struct array, length 2
        NList<Test> testNList = Test.list(mem, 2);
        // get the first test struct.
        Test first = testNList.getFirst();
        // the second
        Test second = testNList.getLast();

        // set value
        first.a(ExampleMacros.DEF_CONST_1);
        System.out.println("read first.a: " + first.a());
        first.b(0.5f);

        // do copy
        int i = ExampleFunctions.copyStruct$int(first, second);
        System.out.println("copyStruct$int return: " + i);

        //memorySegment is also ok
        MemorySegment.copy(first.pointer(), 0, second.pointer(), 0, Test.BYTE_SIZE);

        // pass the pointer of array
        ExampleFunctions.printStruct(testNList);
        // pass the second pointer of array
        ExampleFunctions.printStruct(testNList.subList(1, 1));

        second.a(2);
        second.b(1);
        // pass the second pointer of array
        ExampleFunctions.printStruct(testNList.subList(1, 1));

        VPointer<ReadCallback> callbackFirst = ((ReadCallback) (a, b) -> {
            System.out.println("read first struct callback a:" + a + " b:" + b);
        }).toVPointer(mem);
        ExampleFunctions.readStruct(first, callbackFirst);
        System.err.println(Test.BYTE_SIZE);
        VPointer<ReadCallback> callbackSecond = ((ReadCallback) (a, b) -> {
            System.out.println("read second struct callback a:" + a + " b:" + b);
        }).toVPointer(mem);
        ExampleFunctions.readStructPtr(second, callbackSecond);

        mem.close();
    }
}