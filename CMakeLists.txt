cmake_minimum_required(VERSION 3.27)
project(jbindgen)

set(CMAKE_CXX_STANDARD 23)

# Find CMake file for Clang
find_package(Clang REQUIRED)

include_directories(${CLANG_INCLUDE_DIRS})

file(GLOB analyser "analyser/*.cpp" "analyser/*.h")
file(GLOB generator "generator/*.cpp" "generator/*.h")

add_executable(jbindgen main.cpp ${analyser} ${generator})

#Treat Warnings as Errors
set_property(TARGET jbindgen PROPERTY COMPILE_WARNING_AS_ERROR ON)

target_link_libraries(jbindgen libclang)

include(ExternalProject)
enable_testing()
function(add_jbindgen_test name url source)
    ExternalProject_Add (${name}-src
            URL ${url}
            PREFIX ${CMAKE_CURRENT_BINARY_DIR}/tests/${name}/
            BUILD_IN_SOURCE 1
            CONFIGURE_COMMAND ""
            BUILD_COMMAND ""
            INSTALL_COMMAND "")
    add_executable(${name} ${analyser} ${generator} ${source})
    target_compile_definitions(${name} PRIVATE -DTEST_SRC_DIR="${CMAKE_CURRENT_BINARY_DIR}/tests/${name}/src/${name}-src")
    target_compile_definitions(${name} PRIVATE -DMAIN_SRC_DIR="${CMAKE_CURRENT_SOURCE_DIR}")
    target_link_libraries(${name} libclang)
    #Treat Warnings as Errors
    set_property(TARGET ${name} PROPERTY COMPILE_WARNING_AS_ERROR ON)
    add_dependencies(${name} ${name}-src)
    add_test(NAME ${name} COMMAND ${name})
endfunction()

add_jbindgen_test(miniaudio "https://github.com/mackron/miniaudio/archive/refs/tags/0.11.21.zip" tests/testMiniaudio.cpp)

add_jbindgen_test(freetype "https://gitlab.freedesktop.org/freetype/freetype/-/archive/VER-2-13-2/freetype-VER-2-13-2.zip" tests/testFreeType.cpp)

add_jbindgen_test(assimp "https://github.com/assimp/assimp/archive/refs/tags/v5.3.1.zip" tests/testAssimp.cpp)

add_jbindgen_test(vulkan "https://github.com/KhronosGroup/Vulkan-Headers/archive/refs/tags/v1.3.273.zip" tests/testVulkan.cpp)

add_jbindgen_test(vma "https://github.com/GPUOpen-LibrariesAndSDKs/VulkanMemoryAllocator/archive/refs/tags/v3.0.1.zip" tests/testVMA.cpp)