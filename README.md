jbindgen
====

### A project for generating Java bindings from c code

Usage
----
See the `add_jbindgen_test` function in [CMakeLists.txt](./CMakeLists.txt)

Dependencies
----

- `libclang` 16 or above
- `cmake`
- `g++` or `clang++`

```shell
sudo apt install libclang-16-dev cmake g++
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

Limitation
----
- not guarantee bitfields accessors
- not support function-like macros

License
----
jbindgen is free software dual licensed under the GNU LGPL or MIT License.

You can redistribute it and/or modify it under the terms of the

- GNU Lesser General Public License as published by
  the Free Software Foundation, version 2.1 of the License

or

- MIT License in this repo

jbindgen is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License and
the MIT License along with jbindgen. If not, see <https://www.gnu.org/licenses/>
and <https://opensource.org/licenses/MIT/>.