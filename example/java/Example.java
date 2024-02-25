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
        // Add symbol lookup for the example library
        ExampleSymbols.addSymbols(SymbolLookup.libraryLookup(new File("libexample.so").getAbsolutePath(), mem));

        // Allocate a test struct array with a length of 2
        NList<Test> testNList = Test.list(mem, 2);
        // Get the first test struct
        Test first = testNList.getFirst();
        // Get the second test struct
        Test second = testNList.getLast();

        // Set a value for the 'a' field of the first struct
        first.a(ExampleMacros.DEF_CONST_1);
        System.out.println("Read first.a: " + first.a());

        // Set a value for the 'b' field of the first struct
        first.b(0.5f);

        // Perform a copy of the first struct to the second struct
        int i = ExampleFunctions.copyStruct$int(first, second);
        System.out.println("copyStruct$int return: " + i);

        // Copy memory from the first struct to the second struct using MemorySegment
        MemorySegment.copy(first.pointer(), 0, second.pointer(), 0, Test.BYTE_SIZE);

        // Pass the pointer of the struct array to the printStruct function
        ExampleFunctions.printStruct(testNList);

        // Pass a sublist containing the second struct's pointer to the printStruct function
        ExampleFunctions.printStruct(testNList.subList(1, 1));

        // Update the values of the second struct
        second.a(2);
        second.b(1);

        // Print the updated values of the second struct
        ExampleFunctions.printStruct(testNList.subList(1, 1));

        // Create a callback function for reading the first struct
        VPointer<ReadCallback> callbackFirst = ((ReadCallback) (a, b) -> {
            System.out.println("Read first struct callback. a: " + a + ", b: " + b);
        }).toVPointer(mem);

        // Call the readStruct function with the first struct and the callback
        ExampleFunctions.readStruct(first, callbackFirst);

        // Create a callback function for reading the second struct
        VPointer<ReadCallback> callbackSecond = ((ReadCallback) (a, b) -> {
            System.out.println("Read second struct callback. a: " + a + ", b: " + b);
        }).toVPointer(mem);

        // Call the readStructPtr function with the second struct and the callback
        ExampleFunctions.readStructPtr(second, callbackSecond);

        mem.close();
    }
}