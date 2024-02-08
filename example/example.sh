#! /usr/bin/bash -e
cd "$(dirname "$0")" || exit

echo "compile the file"
cd c
cc example.c --shared -o libexample.so

cd ..

echo "compile & exec jbindgen"
rm -r out
mkdir out
cd out
cmake ../jbindgen
cmake --build . -j #compile
./exampleGenerator #exec

cd ..

echo "copy files"
cp ./java/Example.java out/generation/
cp c/libexample.so out/generation

echo "exec Example.java"
cd out/generation
java --enable-preview --source 22 Example.java
