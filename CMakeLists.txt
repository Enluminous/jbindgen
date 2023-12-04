cmake_minimum_required(VERSION 3.27)
project(jbindgen)

set(CMAKE_CXX_STANDARD 23)

# Find CMake file for Clang
find_package(Clang REQUIRED)

include_directories(${CLANG_INCLUDE_DIRS})

file(GLOB analyser "analyser/*.cpp")
file(GLOB generator "generator/*.cpp")
file(GLOB shared "shared/*.cpp")

add_executable(jbindgen main.cpp ${analyser} ${generator} ${shared})

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
    add_executable(${name} ${analyser} ${generator} ${shared} ${source})
    target_compile_definitions(${name} PRIVATE -DTEST_SRC_DIR="${CMAKE_CURRENT_BINARY_DIR}/tests/${name}/src/${name}-src")
    target_link_libraries(${name} libclang)
    add_dependencies(${name} ${name}-src)
    add_test(NAME ${name} COMMAND ${name})
endfunction()

add_jbindgen_test(miniaudio "https://github.com/mackron/miniaudio/archive/refs/tags/0.11.21.zip" tests/testMiniaudio.cpp)

add_jbindgen_test(freetype "https://gitlab.freedesktop.org/freetype/freetype/-/archive/VER-2-13-2/freetype-VER-2-13-2.zip" tests/testFreeType.cpp)