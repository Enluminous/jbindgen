cmake_minimum_required(VERSION 3.27)
project(jbindgen)

set(CMAKE_CXX_STANDARD 23)

# Find CMake file for Clang
find_package(Clang REQUIRED)

include_directories(${CLANG_INCLUDE_DIRS})

file(GLOB analyser "analyser/*.cpp")
file(GLOB generator "generator/*.cpp")
file(GLOB shared "shared/*.cpp")

add_executable(jbindgen main.cpp ${analyser} ${generator} ${shared}
        generator/SharedGenerator.cpp
        generator/SharedGenerator.h)

#Treat Warnings as Errors
set_property(TARGET jbindgen PROPERTY COMPILE_WARNING_AS_ERROR ON)

target_link_libraries(jbindgen libclang)

enable_testing()
add_test(NAME test COMMAND jbindgen)
