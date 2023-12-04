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
ExternalProject_Add(miniaudio-git
        URL "https://github.com/mackron/miniaudio/archive/refs/tags/0.11.21.zip"
        PREFIX ${CMAKE_CURRENT_BINARY_DIR}/tests/miniaudio/
        BUILD_IN_SOURCE 1
        CONFIGURE_COMMAND ""
        BUILD_COMMAND ""
        INSTALL_COMMAND "")
add_executable(test_miniaudio
        ${analyser} ${generator} ${shared}
        tests/testMiniaudio.cpp)
target_compile_definitions(test_miniaudio PRIVATE -DCURRENT_BUILD_DIR="${CMAKE_CURRENT_BINARY_DIR}")
target_link_libraries(test_miniaudio libclang)
add_dependencies(test_miniaudio miniaudio-git)
add_test(NAME test_miniaudio COMMAND test_miniaudio)