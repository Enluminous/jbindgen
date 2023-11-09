cmake_minimum_required(VERSION 3.27)
project(javabindgen)

set(CMAKE_CXX_STANDARD 17)

# Find CMake file for Clang
find_package(Clang REQUIRED)

include_directories(${CLANG_INCLUDE_DIRS})

add_executable(javabindgen main.cpp
        analyser/Analyser.cpp
        analyser/Analyser.h
        analyser/StructDeclaration.cpp
        analyser/StructDeclaration.h
        analyser/Utils.cpp
        analyser/Utils.h
        analyser/UnionDeclaration.cpp
        analyser/UnionDeclaration.h
        analyser/EnumDeclaration.cpp
        analyser/NormalMacroDeclaration.cpp
        analyser/NormalMacroDeclaration.h
        analyser/FunctionLikeMacroDeclaration.cpp
        analyser/FunctionLikeMacroDeclaration.h
        analyser/NormalTypedefDeclaration.cpp
        analyser/NormalTypedefDeclaration.h
        analyser/FunctionTypedefDeclaration.cpp
        analyser/FunctionTypedefDeclaration.h
        analyser/FunctionDeclaration.cpp
        analyser/FunctionDeclaration.h
        generator/Value.cpp
        generator/Value.h
        generator/StructGeneratorUtils.cpp
        generator/StructGeneratorUtils.h
        generator/StructGenerator.cpp
        generator/StructGenerator.h
        generator/GenUtils.cpp
        generator/GenUtils.h
)

#Treat Warnings as Errors
set_property(TARGET javabindgen PROPERTY COMPILE_WARNING_AS_ERROR ON)

target_link_libraries(javabindgen libclang)
