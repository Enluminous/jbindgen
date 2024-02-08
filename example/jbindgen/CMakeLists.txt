cmake_minimum_required(VERSION 3.27)
project(exampleGenerator)
set(CMAKE_CXX_STANDARD 23)

# Find CMake file for Clang
find_package(Clang REQUIRED)

include_directories(${CLANG_INCLUDE_DIRS})
file(GLOB analyser "${CMAKE_CURRENT_SOURCE_DIR}/../../analyser/*.cpp" "${CMAKE_CURRENT_SOURCE_DIR}/../../analyser/*.h")
file(GLOB generator "${CMAKE_CURRENT_SOURCE_DIR}/../../generator/*.cpp" "${CMAKE_CURRENT_SOURCE_DIR}/../../generator/*.h")
add_executable(exampleGenerator example.cpp ${analyser} ${generator})
add_definitions(-DGEN_FILE="${CMAKE_CURRENT_SOURCE_DIR}/../c/example.h")
target_include_directories(exampleGenerator PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../../)
target_link_libraries(exampleGenerator libclang)