jbindgen
====

### A project for generating Java bindings from c code

Usage
----
See the `add_jbindgen_test` function in [CMakeLists.txt](./CMakeLists.txt)

Dependencies
----

- `libclang` 17 or above
- `cmake`
- `g++` or `clang++`

```shell
sudo apt install libclang-17-dev cmake g++
```

Test & Examples
----
Generate the vulkan bindings, you need to install the `libvulkan-dev` package first

```shell
cmake -B build
cmake --build build -t jbindgen -j
cd build
./jbindgen
```

Generate the [assimp](https://github.com/assimp/assimp) bindings
(need network to download source form GitHub)

```shell
cmake -B build
cmake --build build -t assimp -j 
cd build
./assimp
```

Generate the [miniaudio](https://github.com/mackron/miniaudio) bindings
(need network to download source form GitHub)

```shell
cmake -B build
cmake --build build -t miniaudio -j 
cd build
./miniaudio
```

Generate the [VulkanMemoryAllocator](https://github.com/GPUOpen-LibrariesAndSDKs/VulkanMemoryAllocator) bindings
(need network to download source form GitHub)

```shell
cmake -B build
cmake --build build -t vma -j 
cd build
./vma
```