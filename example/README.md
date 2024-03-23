# Example

This README provides instructions on how to generate and use bindings. The necessary files can be found in the current directory.

```shell
bash -e example.sh # Run a simple example
```

### 1. Find and Compile the C/C++ Files to Generate Bindings

For this example, we will be using [example.h](./c/example.h).

```shell
cd c
cc example.c --shared -o libexample.so # Complie the shared file
```

### 2. Compile and Execute jbindgen

To proceed, you need to create [CMakeLists.txt](jbindgen/CMakeLists.txt) and [example.cpp](jbindgen/example.cpp).
Instructions on how to use them can be found in the file's comments.

```shell
mkdir out
cd out
cmake ../jbindgen
cmake --build . -j # Compile jbindgen
./exampleGenerator # Execute jbindgen
```

### 3. Copy Files

Copy the Java source file and shared library to the generation directory.

```shell
cp ./java/Example.java out/generation/
cp c/libexample.so out/generation
```

### 4. Exec Example.java

Instructions on how to use the bindings can be found in the comments of [Example.java](./java/Example.java).

```shell
cd out/generation
java --enable-preview --source 22 Example.java
```