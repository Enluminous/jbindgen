# example

It's easy to generate bindings,

```shell
bash -e example.sh
```

### 1. find & compile the c/c++ file to generate bindings

[example.h](./c/example.h) will be used for this example

```shell
cd c
cc example.c --shared -o libexample.so
```

### 2. compile & exec jbindgen

You need to write [CMakeLists.txt](./jbindgen/CMakeLists.txt)
and [example.cpp](./jbindgen/example.cpp)

```shell
mkdir out
cd out
cmake ../jbindgen
cmake --build . -j #compile
./exampleGenerator #exec
```

### 3. copy files

Copy the Java src and shared library to the generation dir

```shell
cp ./java/Example.java out/generation/
cp c/libexample.so out/generation
```

### 4. exec Example.java

```shell
cd out/generation
java --enable-preview --source 22 Example.java
```